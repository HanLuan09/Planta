package vn.edu.ptit.planta.ui.plant.search;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.ptit.planta.data.RetrofitClient;
import vn.edu.ptit.planta.model.ApiResponse;
import vn.edu.ptit.planta.model.DataStatus;
import vn.edu.ptit.planta.model.plant.Plant;

public class PlantSearchViewModel extends ViewModel {

    private MutableLiveData<String> textSearch;

    private MutableLiveData<List<Plant>> listPlant;

    private MutableLiveData<DataStatus> dataStatus;

    public PlantSearchViewModel() {
        listPlant = new MutableLiveData<>();
    }

    public MutableLiveData<String> getTextSearch() {
        if(textSearch == null) {
            textSearch = new MutableLiveData<>();
            textSearch.setValue("");
        }
        return textSearch;
    }

    public MutableLiveData<List<Plant>> getListPlant() {
        return listPlant;
    }

    public MutableLiveData<DataStatus> getDataStatus() {
        if(dataStatus == null) {
            dataStatus = new MutableLiveData<>();
            dataStatus.setValue(new DataStatus(false, true,"Danh sách thực vật ở đây"));
        }
        return dataStatus;
    }

    public void handleDataSearch(String key) {
        RetrofitClient.getPlantService().getPlantByName(key).enqueue(new Callback<ApiResponse<List<Plant>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Plant>>> call, Response<ApiResponse<List<Plant>>> response) {
                ApiResponse<List<Plant>> apiResponse = response.body();
                if (response.isSuccessful()){
                    if(apiResponse.isSuccess()){
                        if(apiResponse.getResult() == null || apiResponse.getResult().size() == 0){
                            dataStatus.setValue(new DataStatus(false, true,"Không tìm thấy thực vật"));
                        }else {
                            listPlant.setValue(apiResponse.getResult());
                            getDataStatus().setValue(new DataStatus(true, true, null));
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<List<Plant>>> call, Throwable throwable) {
                getDataStatus().setValue(new DataStatus(false, true,"Không có kết nối"));
            }
        });
    }

}
