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
        if(isData == null) {
            isData = new MutableLiveData<>();
//            isData.setValue(false);
        }
        return isData;
    }

    private void initData() {

        plants = new ArrayList<>();
        RetrofitClient.getPlantService().listPlants().enqueue(new Callback<List<Plant>>() {
            @Override
            public void onResponse(Call<List<Plant>> call, Response<List<Plant>> response) {
                if (response.isSuccessful()) {
                    plants = response.body();
                    listPlants.setValue(plants);
                    isData.setValue(true);
                } else {
                    isData.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<List<Plant>> call, Throwable t) {
                isData.setValue(false);
            }
        });

//        plants = new ArrayList<>();
//        plants.add(new Plant(1, "Hoa hướng dương","", "https://cdn.tgdd.vn/Files/2021/08/03/1372812/dac-diem-nguon-goc-va-y-nghia-dac-biet-cua-hoa-huong-duong-202206031122479117.jpeg"));
//        plants.add(new Plant(2, "Cây chuối", "", "https://i.ex-cdn.com/mientay.giadinhonline.vn/files/content/2023/06/28/cu-cai-da-u-muoi-1057.jpeg"));
//        plants.add(new Plant(3, "Hoa hướng dương","",  "https://cdn.tgdd.vn/Files/2021/08/03/1372812/dac-diem-nguon-goc-va-y-nghia-dac-biet-cua-hoa-huong-duong-202206031122479117.jpeg"));
//        plants.add(new Plant(4, "Hoa hướng dương","", "https://cdn.tgdd.vn/Files/2021/08/03/1372812/dac-diem-nguon-goc-va-y-nghia-dac-biet-cua-hoa-huong-duong-202206031122479117.jpeg"));
//        plants.add(new Plant(5, "Hoa hướng dương", "", "https://cdn.tgdd.vn/Files/2021/08/03/1372812/dac-diem-nguon-goc-va-y-nghia-dac-biet-cua-hoa-huong-duong-202206031122479117.jpeg"));
//        plants.add(new Plant(6, "Cây ngô", "", "https://tuyensinhso.vn/images/files/tuyensinhso.com/nganh-khoa-hoc-cay-trong.jpg"));

//        listPlants.setValue(plants);
    }

    public MutableLiveData<List<Plant>> getListPlants() {
        return listPlants;
    }

    public void onSearchPlant(){
//        if(myPlantNavigator != null) myPlantNavigator.handleSearchMyPlant();
    }
}
