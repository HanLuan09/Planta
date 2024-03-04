package vn.edu.ptit.planta.ui.home.myplant;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import vn.edu.ptit.planta.ui.home.HomeNavigator;

public class MyPlantViewModel extends ViewModel {

    private MyPlantNavigator myPlantNavigator;
    private MutableLiveData<List<String>> listMyPlants;
    private List<String> myPlants;

    public MyPlantViewModel() {
        listMyPlants = new MutableLiveData<>();
        initData();
    }

    public void setMyPlantNavigator(MyPlantNavigator navigator) {
        this.myPlantNavigator = navigator;
    }


    private void initData() {
        myPlants = new ArrayList<>();
        myPlants.add("Hoa hướng dương");
        myPlants.add("Cây Vạn Niên Thanh");
        myPlants.add("Cây ngô");
        myPlants.add("Cây lạc");
        myPlants.add("Hoa hồng");
        myPlants.add("Hoa cúc");

        listMyPlants.setValue(myPlants);
    }

    public MutableLiveData<List<String>> getListMyPlants() {
        return listMyPlants;
    }

    public void onSearchMyPlant(){
        if(myPlantNavigator != null) myPlantNavigator.handleSearchMyPlant();
    }

}
