package vn.edu.ptit.planta.ui.calendarmyplant;

import androidx.lifecycle.ViewModel;

public class CalendarMyPlantViewModel extends ViewModel {

    private CalendarMyPlantNavigator navigator;

    public void setCalendarMyPlantNavigator(CalendarMyPlantNavigator navigator) {
        this.navigator = navigator;
    }

    public void onBackClick() {
        if(navigator != null) navigator.handelBlack();
    }
}
