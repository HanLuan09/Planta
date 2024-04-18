package vn.edu.ptit.planta.ui.home.today;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.ptit.planta.data.RetrofitClient;
import vn.edu.ptit.planta.model.ApiResponse;
import vn.edu.ptit.planta.model.DataStatus;
import vn.edu.ptit.planta.model.care.CareSchedule;
import vn.edu.ptit.planta.model.care.CareScheduleResponse;
import vn.edu.ptit.planta.model.myplant.MyPlantScheduleResponse;
import vn.edu.ptit.planta.model.myschedule.MySchedule;
import vn.edu.ptit.planta.utils.DateUtils;
import vn.edu.ptit.planta.utils.MyPlantCalendarUtils;

public class TodayViewModel extends ViewModel {

    private MutableLiveData<Integer> userId;
    private MutableLiveData<List<MyPlantScheduleResponse>> listMyPlantSchedules;
    private MutableLiveData<List<CareScheduleResponse>> listCareSchedules;
    private List<CareScheduleResponse> careSchedules;
    private MutableLiveData<DataStatus> dataStatus;

    public TodayViewModel() {
        userId = new MutableLiveData<>();
        listMyPlantSchedules = new MutableLiveData<>();
        listCareSchedules = new MutableLiveData<>();
    }

    public MutableLiveData<Integer> getUserId() {
        return userId;
    }

    public MutableLiveData<List<MyPlantScheduleResponse>> getListMyPlantSchedules() {
        return listMyPlantSchedules;
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

    public void initDataMyPlantSchedule() {
        RetrofitClient.getMyPlantService().getMyPlantScheduleByUser(userId.getValue()).enqueue(new Callback<ApiResponse<List<MyPlantScheduleResponse>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<MyPlantScheduleResponse>>> call, Response<ApiResponse<List<MyPlantScheduleResponse>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<List<MyPlantScheduleResponse>> apiResponse = response.body();
                    if (apiResponse.isSuccess()) {
                        careSchedules = MyPlantCalendarUtils.myPlantCalendar(apiResponse.getResult(), selectToday());

                        if(careSchedules == null) dataStatus.setValue(new DataStatus(false, "Không có dữ liệu"));
                        else{
                            listMyPlantSchedules.setValue(apiResponse.getResult());
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
            public void onFailure(Call<ApiResponse<List<MyPlantScheduleResponse>>> call, Throwable throwable) {
                dataStatus.setValue(new DataStatus(false, "Không có kết nối"));
            }
        });
    }


    @NonNull
    private  String selectToday() {
        Calendar calendar = Calendar.getInstance();
        String dateCalendar = String.format(Locale.getDefault(), "%04d-%02d-%02d",
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        return dateCalendar;
    }

}
