package vn.edu.ptit.planta.ui.plant;

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

public class PlantViewModel extends ViewModel {

    private int currentPage = 0;
    private final int PAGE_SIZE = 40;
    private PlantNavigator plantNavigator;
    private MutableLiveData<List<Plant>> listPlants;
    private MutableLiveData<DataStatus> dataStatus;

    public PlantViewModel() {
        listPlants = new MutableLiveData<>();
        initData();
    }
    public void setPlantNavigator(PlantNavigator navigator) {
        this.plantNavigator = navigator;
    }

    public MutableLiveData<DataStatus> getDataStatus() {
        if(dataStatus == null) {
            dataStatus = new MutableLiveData<>();
            dataStatus.setValue(new DataStatus(false, "Đang kết nối"));
        }
        return dataStatus;
    }

    private void initData() {
        RetrofitClient.getPlantService().listPlants().enqueue(new Callback<ApiResponse<List<Plant>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Plant>>> call, Response<ApiResponse<List<Plant>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<List<Plant>> apiResponse = response.body();
                    if(apiResponse.isSuccess()){
                        if(apiResponse.getResult() == null) dataStatus.setValue(new DataStatus(false, "Không có dữ liệu"));
                        else {
                            listPlants.setValue(apiResponse.getResult());
                            getDataStatus().setValue(new DataStatus(true, null));
                        }

                    }else {
                        getDataStatus().setValue(new DataStatus(false, apiResponse.getMessage()));
                    }
                } else {
                    getDataStatus().setValue(new DataStatus(false, "Kết nối thất bại"));
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Plant>>> call, Throwable t) {
                getDataStatus().setValue(new DataStatus(false, "Không có kết nối"));
            }
        });

    }

    public void fetchData() {
        RetrofitClient.getPlantService().listPlantPage(currentPage, PAGE_SIZE).enqueue(new Callback<ApiResponse<List<Plant>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Plant>>> call, Response<ApiResponse<List<Plant>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<List<Plant>> apiResponse = response.body();
                    if (apiResponse.isSuccess() && apiResponse.getResult() != null) {
                        List<Plant> currentPlants = listPlants.getValue();
                        if (currentPlants != null) {
                            currentPlants.addAll(apiResponse.getResult());
                            listPlants.setValue(currentPlants);
                        }else {
                            listPlants.setValue(apiResponse.getResult());
                        }
                        dataStatus.setValue(new DataStatus(true, null));
                        currentPage++;
                    }
                }else {
                    dataStatus.setValue(new DataStatus(false, "Kết nối thất bại"));
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Plant>>> call, Throwable t) {
                dataStatus.setValue(new DataStatus(false, "Không có kết nối"));
            }
        });
    }

    public MutableLiveData<List<Plant>> getListPlants() {
        return listPlants;
    }

    public void onSearchPlantClick(){
        if(plantNavigator != null) plantNavigator.handlePlantSearch();
    }
}
