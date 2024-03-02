package vn.edu.ptit.planta.ui.schedule.notification;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Calendar;
import java.util.Locale;

import vn.edu.ptit.planta.ui.home.HomeNavigator;

public class AddNotificationViewModel extends ViewModel {

    private NotificationNavigator notificationNavigator;
    private MutableLiveData<Integer> checkDialog;
    private MutableLiveData<String> exercise;
    private MutableLiveData<String> time;
    private MutableLiveData<String> startDate;
    private MutableLiveData<String> endDate;
    private MutableLiveData<Integer> frequency;

    public void setNotificationNavigator(NotificationNavigator navigator) {
        this.notificationNavigator = navigator;
    }

    public MutableLiveData<Integer> getCheckDialog() {
        if(checkDialog == null) {
            checkDialog = new MutableLiveData<>();
            checkDialog.setValue(0);
        }
        return checkDialog;
    }

    public MutableLiveData<String> getExercise() {
        if(exercise == null) {
            exercise = new MutableLiveData<>();
            exercise.setValue("Tưới nuớc");
        }
        return exercise;
    }

    public MutableLiveData<String> getTime() {
        if(time == null) {
            time = new MutableLiveData<>();
            time.setValue("08:00");
        }
        return time;
    }

    public MutableLiveData<String> getStartDate() {
        if(startDate == null) {
            startDate = new MutableLiveData<>();
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            String selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);
            startDate.setValue(selectedDate);
        }
        return startDate;
    }
    public MutableLiveData<String> getEndDate() {
        if(endDate == null) {
            endDate = new MutableLiveData<>();
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            String selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);
            endDate.setValue(selectedDate);
        }
        return endDate;
    }

    public MutableLiveData<Integer> getFrequency() {
        if(frequency == null) {
            frequency = new MutableLiveData<>();
            frequency.setValue(2);
        }
        return frequency;
    }


    public void onCloseNotificationClick() {
        if(notificationNavigator != null) notificationNavigator.handleCloseNotification();
    }

    public void onSummitNotificationClick() {
        if(notificationNavigator != null) notificationNavigator.handleSummitNotification();
    }
}
