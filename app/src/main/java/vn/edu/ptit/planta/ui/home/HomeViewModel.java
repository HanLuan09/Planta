package vn.edu.ptit.planta.ui.home;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private HomeNavigator homeNavigator;
    public HomeViewModel() {}

    public void setHomeNavigator(HomeNavigator navigator) {
        this.homeNavigator = navigator;
    }

    public void onMyPlantClick() {
        if (homeNavigator != null) homeNavigator.handleMyPlantFragment();
    }

    public void onCalendarMyPlantClick() {
        if (homeNavigator != null) homeNavigator.handleCalendarMyPlant();
    }

    public  void onAddMyGardenClick() {
        if (homeNavigator != null) homeNavigator.handleAddMyGarden();
    }

}
