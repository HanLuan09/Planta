package vn.edu.ptit.planta.ui.schedule.notification;

import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.Observer;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.ptit.planta.data.RetrofitClient;
import vn.edu.ptit.planta.model.ApiResponse;
import vn.edu.ptit.planta.model.Plant;
import vn.edu.ptit.planta.model.myschedule.MySchedule;
import vn.edu.ptit.planta.model.myschedule.MyScheduleRequest;
import vn.edu.ptit.planta.utils.DateUtils;

public class AddNotificationViewModel extends ViewModel {

    private NotificationNavigator notificationNavigator;
    private MutableLiveData<Integer> checkDialog;
    private MutableLiveData<Integer> idMySchedule;
    private MutableLiveData<String> exercise;
    private MutableLiveData<String> time;
    private MutableLiveData<String> startDate;
    private MutableLiveData<String> endDate;
    private MutableLiveData<Integer> frequency;
    private MutableLiveData<Boolean> isCheckEndDate;
    private MutableLiveData<Boolean> busy;
    private MutableLiveData<Boolean> isCheckEdit;

    private boolean edit = false;


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

    public MutableLiveData<Integer> getIdMySchedule() {
        if(idMySchedule == null) idMySchedule = new MutableLiveData<>();
        return idMySchedule;
    }
    public MutableLiveData<String> getExercise() {
        if(exercise == null) {
            exercise = new MutableLiveData<>();
            exercise.setValue("Tưới nước");
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
                if(notificationNavigator != null) {
                    if(!edit) notificationNavigator.handleSummitNotification();
                    else {
                        notificationNavigator.handleEditSummitNotification();
                        edit = false;
                    }
                }

            }
        }, 3000);
    }

    public void onEditClick() {
        isCheckEdit.setValue(false);
        edit = true;
    }

    public void onDeleteClick() {
        if(notificationNavigator != null) notificationNavigator.handleDeleteNotification();
    }

    public void handleAddMySchedule(MyScheduleRequest request, boolean isAdd) {

        if(isAdd == true){
            RetrofitClient.getMyScheduleService().createMyScheduleByPlant(request).enqueue(new Callback<MySchedule>() {
                @Override
                public void onResponse(Call<MySchedule> call, Response<MySchedule> response) {
                    notificationNavigator.handleDialogScheduleSuccess("Xóa lịch trình không thành công");
                }

                @Override
                public void onFailure(Call<MySchedule> call, Throwable throwable) {
                    notificationNavigator.handleDialogScheduleFail("Xóa lịch trình không thành công");
                }
            });
        }else {
            if(idMySchedule != null && idMySchedule.getValue() != null) {
                RetrofitClient.getMyScheduleService().updateMyScheduleByPlant(idMySchedule.getValue(), request).enqueue(new Callback<MySchedule>() {
                    @Override
                    public void onResponse(Call<MySchedule> call, Response<MySchedule> response) {
                        notificationNavigator.handleDialogScheduleSuccess("Sửa lịch trình thành công");
                    }

                    @Override
                    public void onFailure(Call<MySchedule> call, Throwable throwable) {
                        notificationNavigator.handleDialogScheduleFail("Xóa lịch trình không thành công");
                    }
                });
            }
        }
    }
    public void handleDeleteMySchedule(){
        if(idMySchedule != null && idMySchedule.getValue() != null) {
            RetrofitClient.getMyScheduleService().deleteMySchedule(idMySchedule.getValue()).enqueue(new Callback<MySchedule>() {
                @Override
                public void onResponse(Call<MySchedule> call, Response<MySchedule> response) {
                    notificationNavigator.handleDialogScheduleSuccess("Xóa lịch trình thành công");
                    notificationNavigator.handleResult();
                }

                @Override
                public void onFailure(Call<MySchedule> call, Throwable throwable) {
                    notificationNavigator.handleDialogScheduleFail("Xóa lịch trình không thành công");
                }
            });
        }else{
            Log.e("Delete", "faile");
        }
    }
}
