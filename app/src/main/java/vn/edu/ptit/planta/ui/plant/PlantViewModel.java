package vn.edu.ptit.planta.ui.plant;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import vn.edu.ptit.planta.ui.home.myplant.MyPlantNavigator;

public class PlantViewModel extends ViewModel {

    private PlantNavigator plantNavigator;
    private MutableLiveData<List<String>> listPlants;

    private List<String> plants;

    public PlantViewModel() {
        listPlants = new MutableLiveData<>();
        initData();
    }
    public void setPlantNavigator(PlantNavigator navigator) {
        this.plantNavigator = navigator;
    }


    private void initData() {
        plants = new ArrayList<>();
        plants.add("Hoa hướng dương");
        plants.add("Cây Vạn Niên Thanh");
        plants.add("Cây ngô");
        plants.add("Cây lạc");
        plants.add("Hoa hồng");
        plants.add("Hoa cúc");

        listPlants.setValue(plants);
    }

    public MutableLiveData<List<String>> getListPlants() {
        return listPlants;
    }

    public void onSearchPlant(){
//        if(myPlantNavigator != null) myPlantNavigator.handleSearchMyPlant();
    }

}
