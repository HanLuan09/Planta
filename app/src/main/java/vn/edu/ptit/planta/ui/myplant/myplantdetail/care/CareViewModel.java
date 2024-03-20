package vn.edu.ptit.planta.ui.myplant.myplantdetail.care;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import vn.edu.ptit.planta.model.ScheduleMyPlant;

public class CareViewModel extends ViewModel {

    private CareNavigator careNavigator;

    private MutableLiveData<List<ScheduleMyPlant>> listSchedules;

    private List<ScheduleMyPlant> schedules;

    public CareViewModel() {
        listSchedules = new MutableLiveData<>();
        initData();
    }

    public void setCareNavigator(CareNavigator careNavigator) {
        this.careNavigator = careNavigator;
    }

    public MutableLiveData<List<ScheduleMyPlant>> getListSchedules() {
        return listSchedules;
    }

    private void initData() {

        schedules = new ArrayList<>();
        schedules.add(new ScheduleMyPlant(1, "Tưới nước", "8:30"));
        schedules.add(new ScheduleMyPlant(2, "Tưới nước", "18:30"));
        schedules.add(new ScheduleMyPlant(3, "Bón phân", "12:00"));
        schedules.add(new ScheduleMyPlant(4, "Tưới nước", "9:30"));
        schedules.add(new ScheduleMyPlant(5, "Bón phân", "9:30"));
        schedules.add(new ScheduleMyPlant(6, "Tưới nước", "9:30"));
        schedules.add(new ScheduleMyPlant(7, "Bón phân", "9:30"));
        schedules.add(new ScheduleMyPlant(8, "Tưới nước", "9:30"));
        schedules.add(new ScheduleMyPlant(9, "Tưới nước", "9:30"));


        listSchedules.setValue(schedules);
    }
}
