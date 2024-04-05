package vn.edu.ptit.planta.ui.plant;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.ptit.planta.data.RetrofitClient;
import vn.edu.ptit.planta.data.service.PlantService;
import vn.edu.ptit.planta.model.ApiResponse;
import vn.edu.ptit.planta.model.Plant;

public class PlantViewModel extends ViewModel {

    private PlantNavigator plantNavigator;
    private MutableLiveData<List<Plant>> listPlants;
    private MutableLiveData<Boolean> isData;

    private List<Plant> plants;

    public PlantViewModel() {
        listPlants = new MutableLiveData<>();
        initData();
    }
    public void setPlantNavigator(PlantNavigator navigator) {
        this.plantNavigator = navigator;
    }

    public MutableLiveData<Boolean> getIsData() {
        if(isData == null) isData = new MutableLiveData<>();
        return isData;
    }

    private void initData() {

        plants = new ArrayList<>();
        RetrofitClient.getPlantService().listPlants().enqueue(new Callback<ApiResponse<List<Plant>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Plant>>> call, Response<ApiResponse<List<Plant>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<List<Plant>> apiResponse = response.body();
                    if(apiResponse.isSuccess()){
                        plants = apiResponse.getResult();
                        listPlants.setValue(plants);
                        isData.setValue(true);
                    }else {
                        isData.setValue(false);
                    }
                } else {
                    isData.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Plant>>> call, Throwable t) {
                isData.setValue(false);
            }
        });

    }

    public MutableLiveData<List<Plant>> getListPlants() {
        return listPlants;
    }

    public void onSearchPlant(){
//        if(myPlantNavigator != null) myPlantNavigator.handleSearchMyPlant();
    }
}
