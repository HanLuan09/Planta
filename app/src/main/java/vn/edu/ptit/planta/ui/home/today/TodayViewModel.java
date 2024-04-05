package vn.edu.ptit.planta.ui.home.today;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.ptit.planta.data.RetrofitClient;
import vn.edu.ptit.planta.model.ApiResponse;
import vn.edu.ptit.planta.model.DataStatus;
import vn.edu.ptit.planta.model.care.CareScheduleResponse;

public class TodayViewModel extends ViewModel {

    private MutableLiveData<List<CareScheduleResponse>> listCareSchedules;
    private List<CareScheduleResponse> careSchedules;
    private MutableLiveData<DataStatus> dataStatus;

    public TodayViewModel() {
        listCareSchedules = new MutableLiveData<>();
        initData();
    }
    public MutableLiveData<List<CareScheduleResponse>> getListCareSchedules() {
        return listCareSchedules;
    }
    public MutableLiveData<DataStatus> getDataStatus() {
        if(dataStatus == null) {
            dataStatus = new MutableLiveData<>();
            dataStatus.setValue(new DataStatus(false, "Đang kết nối"));
        }
        return dataStatus;
    }

    public void initData() {
        RetrofitClient.getMyPlantService().getMyPlantToDayByUser(1).enqueue(new Callback<ApiResponse<List<CareScheduleResponse>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<CareScheduleResponse>>> call, Response<ApiResponse<List<CareScheduleResponse>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<List<CareScheduleResponse>> apiResponse = response.body();
                    if (apiResponse.isSuccess()) {
                        careSchedules = apiResponse.getResult();
                        if(careSchedules == null) dataStatus.setValue(new DataStatus(false, "Không có dữ liệu"));
                        else{
                            listCareSchedules.setValue(careSchedules);
                            dataStatus.setValue(new DataStatus(true, null));
                        }
                    }else{
                        dataStatus.setValue(new DataStatus(false, apiResponse.getMessage()));
                    }
                }
                else {
                    dataStatus.setValue(new DataStatus(false, "Kết nối thất bại"));
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<CareScheduleResponse>>> call, Throwable throwable) {
                dataStatus.setValue(new DataStatus(false, "Không có kết nối"));
            }
        });
    }
}
