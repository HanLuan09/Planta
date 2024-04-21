package vn.edu.ptit.planta.ui.myplant;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.ptit.planta.data.RetrofitClient;
import vn.edu.ptit.planta.model.ApiResponse;
import vn.edu.ptit.planta.model.myplant.MyPlant;

public class MyPlantViewModel extends ViewModel {
    private MyPlantNavigator myPlantNavigator;
    private MutableLiveData<List<MyPlant>> listMyPlants;
    private MutableLiveData<Boolean> isData;
    private List<MyPlant> myPlants;
    private int idUserResponse;

    public MyPlantViewModel(){
        listMyPlants = new MutableLiveData<>();
    }

    public void setMyPlantNavigator(MyPlantNavigator navigator) {
        this.myPlantNavigator = navigator;
    }

    public void setIdUserResponse(int id){
        this.idUserResponse = id;
        initData();
    }

    public MutableLiveData<Boolean> getIsData() {
        if(isData == null) isData = new MutableLiveData<>();
        return isData;
    }
    private void initData() {
        myPlants = new ArrayList<>();

        RetrofitClient.getMyPlantService().getAllMyPlant(idUserResponse).enqueue(new Callback<ApiResponse<List<MyPlant>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<MyPlant>>> call, Response<ApiResponse<List<MyPlant>>> response) {
                if(response.isSuccessful()){
                    ApiResponse<List<MyPlant>> apiResponse = response.body();
                    if (apiResponse.isSuccess()){
                        myPlants = apiResponse.getResult();
                        listMyPlants.setValue(myPlants);

                        if(myPlants.size() == 0){
                            isData.setValue(false);
                        }
                        else {
                            isData.setValue(true);
                        }
                    }
                    else{
                        isData.setValue(false);
                    }
                }
                else{
                    isData.setValue(false);
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<List<MyPlant>>> call, Throwable throwable) {
                isData.setValue(false);
            }
        });
    }

    public MutableLiveData<List<MyPlant>> getListMyPlants() {
        return listMyPlants;
    }

    public void onSearchMyPlant(){
        if(myPlantNavigator != null) myPlantNavigator.handleSearchMyPlant();
    }

}
