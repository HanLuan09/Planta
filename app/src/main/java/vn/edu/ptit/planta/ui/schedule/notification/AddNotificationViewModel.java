package vn.edu.ptit.planta.ui.schedule.notification;

import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.Observer;

import java.util.Calendar;
import java.util.Locale;

import vn.edu.ptit.planta.model.myschedule.MySchedule;
import vn.edu.ptit.planta.utils.DateUtils;

public class AddNotificationViewModel extends ViewModel {

    private NotificationNavigator notificationNavigator;
    private MutableLiveData<Integer> checkDialog;
    private MutableLiveData<String> exercise;
    private MutableLiveData<String> time;
    private MutableLiveData<String> startDate;

    private MutableLiveData<String> endDate;

    private MutableLiveData<Integer> frequency;

    private MutableLiveData<Boolean> isCheckEndDate;
    private MutableLiveData<Boolean> busy;

    private MutableLiveData<Boolean> isCheckEdit;


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
    public void setEndDate(MutableLiveData<String> endDate) {
        this.endDate = endDate;
    }

    public MutableLiveData<Integer> getFrequency() {
        if(frequency == null) {
            frequency = new MutableLiveData<>();
        }
        return frequency;
    }

    public MutableLiveData<Boolean> getIsCheckEndDate() {
        if(isCheckEndDate == null) {
            isCheckEndDate = new MutableLiveData<>();
        }
        return isCheckEndDate;
    }

    public MutableLiveData<Boolean> getBusy() {
        if (busy == null) {
            busy = new MutableLiveData<>();
            busy.setValue(false);
        }

        return busy;
    }

    public MutableLiveData<Boolean> getIsCheckEdit() {
        if(isCheckEdit == null) {
            isCheckEdit = new MutableLiveData<>();
            isCheckEdit.setValue(false);
        }
        return isCheckEdit;
    }

    public void onStartDatePickerDialog(){
        if(!isCheckEdit.getValue() && notificationNavigator != null){
            notificationNavigator.handleStartDatePickerDialog();
        }
    }
    public void onEndDatePickerDialog(){
        if(!isCheckEdit.getValue() && notificationNavigator != null)
            notificationNavigator.handleEndDatePickerDialog();
    }

    public void onFrequencyDialog(){
        if(!isCheckEdit.getValue() && notificationNavigator != null)
            notificationNavigator.handleFrequencyDialog();
    }

    public void onExerciseDialog(){
        if(!isCheckEdit.getValue() && notificationNavigator != null)
            notificationNavigator.handleExerciseDialog();
    }

    public void onTimePickerDialog(){
        if(!isCheckEdit.getValue() &&notificationNavigator != null)
            notificationNavigator.handleTimePickerDialog();
    }

    public void onCloseNotificationClick() {
        if(notificationNavigator != null) notificationNavigator.handleCloseNotification();
    }

    public void onSummitNotificationClick() {
        getBusy().setValue(true); //View.VISIBLE
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                busy.setValue(false); // == View.GONE
                if(notificationNavigator != null) notificationNavigator.handleSummitNotification();

            }
        }, 3000);
    }

    public void onEditClick() {
        isCheckEdit.setValue(false);
    }

    public void onDeleteClick() {
        if(notificationNavigator != null) notificationNavigator.handleDeleteNotification();
    }

}
