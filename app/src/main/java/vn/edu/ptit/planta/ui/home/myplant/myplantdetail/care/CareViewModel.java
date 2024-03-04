package vn.edu.ptit.planta.ui.home.myplant.myplantdetail.care;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import vn.edu.ptit.planta.model.Schedule;

public class CareViewModel extends ViewModel {

    private CareNavigator careNavigator;

    private MutableLiveData<List<Schedule>> listSchedules;

    private List<Schedule> schedules;

    public CareViewModel() {
        listSchedules = new MutableLiveData<>();
        initData();
    }

    public void setCareNavigator(CareNavigator careNavigator) {
        this.careNavigator = careNavigator;
    }

    public MutableLiveData<List<Schedule>> getListSchedules() {
        return listSchedules;
    }

    private void initData() {

        schedules = new ArrayList<>();
        schedules.add(new Schedule(1, "Tưới nước", "8:30"));
        schedules.add(new Schedule(2, "Tưới nước", "18:30"));
        schedules.add(new Schedule(3, "Bón phân", "12:00"));
        schedules.add(new Schedule(4, "Tưới nước", "9:30"));
        schedules.add(new Schedule(5, "Bón phân", "9:30"));
        schedules.add(new Schedule(6, "Tưới nước", "9:30"));
        schedules.add(new Schedule(7, "Bón phân", "9:30"));
        schedules.add(new Schedule(8, "Tưới nước", "9:30"));
        schedules.add(new Schedule(9, "Tưới nước", "9:30"));


        listSchedules.setValue(schedules);
    }
}
