package vn.edu.ptit.planta.ui.myplant.myplantdetail.care;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.ptit.planta.data.RetrofitClient;
import vn.edu.ptit.planta.model.ApiResponse;
import vn.edu.ptit.planta.model.care.CareCalendar;
import vn.edu.ptit.planta.model.care.CareCalendarResponse;
import vn.edu.ptit.planta.model.myschedule.MySchedule;
import vn.edu.ptit.planta.utils.DateUtils;
import vn.edu.ptit.planta.utils.MyPlantCalendarUtils;

public class CareViewModel extends ViewModel {

    private CareNavigator careNavigator;

    private MutableLiveData<Integer> idMyPlant;

    private MutableLiveData<List<MySchedule>> listSchedules;

    private MutableLiveData<List<CareCalendarResponse>> listCareCalendars;

    public CareViewModel() {
        idMyPlant = new MutableLiveData<>();
        listSchedules = new MutableLiveData<>();
        listCareCalendars = new MutableLiveData<>();
    }

    public void setCareNavigator(CareNavigator careNavigator) {
        this.careNavigator = careNavigator;
    }
    public MutableLiveData<Integer> getIdMyPlant() {
        return idMyPlant;
    }

    public MutableLiveData<List<MySchedule>> getListSchedules() {
        return listSchedules;
    }
    public MutableLiveData<List<CareCalendarResponse>> getListCareCalendars() {
        return listCareCalendars;
    }

    public void initDataSchedule() {
        RetrofitClient.getMyScheduleService().myScheduleByPlant(idMyPlant.getValue()).enqueue(new Callback<ApiResponse<List<MySchedule>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<MySchedule>>> call, Response<ApiResponse<List<MySchedule>>> response) {
                if(response.isSuccessful()){
                    ApiResponse<List<MySchedule>> apiResponse = response.body();
                    listSchedules.setValue(apiResponse.getResult());
                    listCareCalendars.setValue(MyPlantCalendarUtils.myCareCalendar(apiResponse.getResult()));
                }else{

                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<MySchedule>>> call, Throwable throwable) {
                Log.e("test 2", "Error: " + throwable.toString());

            }
        });

    }

    public void initDataCareCalendar() {
        RetrofitClient.getMyScheduleService().getMyCareCalendarByMyPlantId(idMyPlant.getValue()).enqueue(new Callback<ApiResponse<List<CareCalendarResponse>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<CareCalendarResponse>>> call, Response<ApiResponse<List<CareCalendarResponse>>> response) {

                if (response.isSuccessful()){
                    ApiResponse<List<CareCalendarResponse>> apiResponse = response.body();
                    listCareCalendars.setValue(apiResponse.getResult());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<CareCalendarResponse>>> call, Throwable throwable) {

            }
        });

    }

    public void onAddNotificationClick(){
        if(careNavigator != null) careNavigator.handleAddNotification();
    }

}
