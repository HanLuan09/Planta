package vn.edu.ptit.planta.ui.calendarmyplant;


import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.ptit.planta.data.RetrofitClient;
import vn.edu.ptit.planta.model.ApiResponse;
import vn.edu.ptit.planta.model.myplant.MyPlantScheduleResponse;
import vn.edu.ptit.planta.model.care.CareSchedule;
import vn.edu.ptit.planta.model.care.CareScheduleResponse;
import vn.edu.ptit.planta.model.myschedule.MySchedule;
import vn.edu.ptit.planta.utils.DateUtils;
import vn.edu.ptit.planta.utils.MyPlantCalendarUtils;

public class CalendarMyPlantViewModel extends ViewModel {

    private CalendarMyPlantNavigator navigator;

    private MutableLiveData<Integer> userId;

    private MutableLiveData<List<CareScheduleResponse>> listCareSchedules;

    private MutableLiveData<List<MyPlantScheduleResponse>> listMyPlantSchedules;
    private MutableLiveData<String> daySelect;

    public CalendarMyPlantViewModel() {
        userId = new MutableLiveData<>();
        listCareSchedules = new MutableLiveData<>();
        listMyPlantSchedules = new MutableLiveData<>();
        daySelect = new MutableLiveData<>();
        Calendar calendar = Calendar.getInstance();
        String selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d",
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        daySelect.setValue(selectedDate);
    }

    public void setCalendarMyPlantNavigator(CalendarMyPlantNavigator navigator) {
        this.navigator = navigator;
    }

    public MutableLiveData<Integer> getUserId() {
        return userId;
    }

    public MutableLiveData<List<MyPlantScheduleResponse>> getListMyPlantSchedules() {
        return listMyPlantSchedules;
    }

    public MutableLiveData<List<CareScheduleResponse>> getListCareSchedules() {
        return listCareSchedules;
    }

    public MutableLiveData<String> getDaySelect() {
        return daySelect;
    }
    public void initData() {
        RetrofitClient.getMyPlantService().getAllMyPlantCalendarByUser(userId.getValue()).enqueue(new Callback<ApiResponse<List<MyPlantScheduleResponse>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<MyPlantScheduleResponse>>> call, Response<ApiResponse<List<MyPlantScheduleResponse>>> response) {
                if(response.isSuccessful()) {
                    ApiResponse<List<MyPlantScheduleResponse>> apiResponse = response.body();
                    List<MyPlantScheduleResponse> myPlantScheduleResponses = apiResponse.getResult();
                    listMyPlantSchedules.setValue(myPlantScheduleResponses);
                    listCareSchedules.setValue(MyPlantCalendarUtils.myPlantCalendar(myPlantScheduleResponses, daySelect.getValue()));
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<MyPlantScheduleResponse>>> call, Throwable throwable) {

            }
        });
    }

    public List<CareScheduleResponse> myPlantToDayByUser(@NonNull List<MyPlantScheduleResponse> myPlants, String dateCalendar) {

        if(myPlants == null) return null;

        Date currentDate = DateUtils.stringToDate(dateCalendar);

        Map<String, List<CareSchedule>> groupedSchedules = new HashMap<>();

        // Nhóm các CareSchedule theo tên bài tập
        for (MyPlantScheduleResponse myPlant : myPlants) {
            List<MySchedule> mySchedules = myPlant.getMySchedules();
            for (MySchedule mySchedule : mySchedules) {

                if ((currentDate.after(mySchedule.getStartDate()) || currentDate.equals(mySchedule.getStartDate())) &&
                        (currentDate.before(mySchedule.getEndDate()) || currentDate.equals(mySchedule.getEndDate()))) {
                    if(DateUtils.diffDays(mySchedule.getStartDate(), currentDate) % mySchedule.getFrequency() == 0) {
                        String scheduleName = mySchedule.getName();
                        CareSchedule careSchedule = new CareSchedule(
                                myPlant.getId(),
                                myPlant.getName(),
                                myPlant.getImage(),
                                mySchedule.getStartDate(),
                                mySchedule.getEndDate(),
                                mySchedule.getTime(),
                                mySchedule.getFrequency()
                        );

                        groupedSchedules.computeIfAbsent(scheduleName, k -> new ArrayList<>()).add(careSchedule);
                    }
                }
            }
        }

        // Tạo danh sách MyCareScheduleResponse từ map đã nhóm
        List<CareScheduleResponse> scheduleResponses = new ArrayList<>();
        for (Map.Entry<String, List<CareSchedule>> entry : groupedSchedules.entrySet()) {
            CareScheduleResponse myCareScheduleResponse = new CareScheduleResponse();
            myCareScheduleResponse.setName(entry.getKey());
            myCareScheduleResponse.setCareSchedules(entry.getValue());
            scheduleResponses.add(myCareScheduleResponse);
        }

        return scheduleResponses;
    }


    public void onBackClick() {
        if(navigator != null) navigator.handelBlack();
    }
}
