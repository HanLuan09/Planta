package vn.edu.ptit.planta.ui.myplant.myplantdetail.care;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.ptit.planta.data.RetrofitClient;
import vn.edu.ptit.planta.model.myschedule.MySchedule;

public class CareViewModel extends ViewModel {

    private CareNavigator careNavigator;

    private MutableLiveData<List<MySchedule>> listSchedules;

    private List<MySchedule> schedules;

    public CareViewModel() {
        listSchedules = new MutableLiveData<>();
        initData();
    }

    public void setCareNavigator(CareNavigator careNavigator) {
        this.careNavigator = careNavigator;
    }

    public MutableLiveData<List<MySchedule>> getListSchedules() {
        return listSchedules;
    }

    private void initData() {

        schedules = new ArrayList<>();
        RetrofitClient.getMyScheduleService().myScheduleByPlant(1).enqueue(new Callback<List<MySchedule>>() {
            @Override
            public void onResponse(Call<List<MySchedule>> call, Response<List<MySchedule>> response) {
                if(response.isSuccessful()){
                    schedules = response.body();
                    listSchedules.setValue(schedules);

                }else{

                }
            }

            @Override
            public void onFailure(Call<List<MySchedule>> call, Throwable throwable) {
                Log.e("test 2", "Error: " + throwable.toString());

            }
        });

    }

    public void onAddNotificationClick(){
        if(careNavigator != null) careNavigator.handleAddNotification();
    }

}
