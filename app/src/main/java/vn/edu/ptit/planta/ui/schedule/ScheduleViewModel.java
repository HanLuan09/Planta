package vn.edu.ptit.planta.ui.schedule;

import android.os.Handler;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import vn.edu.ptit.planta.model.ScheduleNodtification;

public class ScheduleViewModel extends ViewModel {
    private MutableLiveData<Boolean> busy;
    private MutableLiveData<List<ScheduleNodtification>> listSchedules;
    private List<ScheduleNodtification> schedules;
    private ScheduleNavigator scheduleNavigator;

    public void setScheduleNavigator(ScheduleNavigator navigator) {
        this.scheduleNavigator = navigator;
    }

    public MutableLiveData<List<ScheduleNodtification>> getListSchedules() {
        if(listSchedules == null) {
            listSchedules = new MutableLiveData<>();
        }
        return listSchedules;
    }

    public MutableLiveData<Boolean> getBusy() {

        if (busy == null) {
            busy = new MutableLiveData<>();
            busy.setValue(false);
        }

        return busy;
    }

    public void addScheduleNodtification(ScheduleNodtification s) {
        if(schedules == null) schedules = new ArrayList<>();
        schedules.add(s);
        if(listSchedules == null) listSchedules = new MutableLiveData<>();
        listSchedules.setValue(schedules);
    }

    public void onAddNotificationClick(){
        if(scheduleNavigator != null) scheduleNavigator.handleAddNotification();
    }

    public void handleSubmit() {
        getBusy().setValue(true); //View.VISIBLE
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                busy.setValue(false); // == View.GONE
                scheduleNavigator.handleBlackNotification();

            }
        }, 3000);
    }
}
