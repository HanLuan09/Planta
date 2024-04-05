package vn.edu.ptit.planta.ui.schedule;

import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.ptit.planta.data.RetrofitClient;
import vn.edu.ptit.planta.model.ApiResponse;
import vn.edu.ptit.planta.model.myschedule.MySchedule;

public class ScheduleViewModel extends ViewModel {

    private MutableLiveData<Integer> idMyPlant;
    private MutableLiveData<Boolean> busy;
    private MutableLiveData<List<MySchedule>> listSchedules;
    private ScheduleNavigator scheduleNavigator;
    private MutableLiveData<Boolean> isCheckBlack;

    public ScheduleViewModel() {
        idMyPlant = new MutableLiveData<>();
        listSchedules = new MutableLiveData<>();
    }

    public void setScheduleNavigator(ScheduleNavigator navigator) {
        this.scheduleNavigator = navigator;
    }

    public MutableLiveData<List<MySchedule>> getListSchedules() {
        return listSchedules;
    }

    public MutableLiveData<Integer> getIdMyPlant() {
        return idMyPlant;
    }

    public MutableLiveData<Boolean> getIsCheckBlack() {
        if(isCheckBlack == null) isCheckBlack = new MutableLiveData<>();
        return isCheckBlack;
    }
    public MutableLiveData<Boolean> getBusy() {

        if (busy == null) {
            busy = new MutableLiveData<>();
            busy.setValue(false);
        }

        return busy;
    }

    public void initDataSchedule() {
        RetrofitClient.getMyScheduleService().myScheduleByPlant(idMyPlant.getValue()).enqueue(new Callback<ApiResponse<List<MySchedule>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<MySchedule>>> call, Response<ApiResponse<List<MySchedule>>> response) {
                if(response.isSuccessful()){
                    ApiResponse<List<MySchedule>> apiResponse = response.body();
                    listSchedules.setValue(apiResponse.getResult());
                    isCheckBlack.setValue(listSchedules.getValue().size()>0? true: false);
                }else{

                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<MySchedule>>> call, Throwable throwable) {
                Log.e("test 2", "Error: " + throwable.toString());

            }
        });

    }

    public void onAddNotificationClick(){
        if(scheduleNavigator != null) scheduleNavigator.handleAddNotification();
    }

    public void handleSubmit() {
        getBusy().setValue(true); //View.VISIBLE
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                scheduleNavigator.handleBlackNotification();
                busy.setValue(false); // == View.GONE

            }
        }, 3000);
    }
    public void onBlackClick(){
        if(scheduleNavigator != null) scheduleNavigator.handleBlackNotification();
    }
}
