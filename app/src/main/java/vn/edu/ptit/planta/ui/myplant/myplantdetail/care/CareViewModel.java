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
                    listCareCalendars.setValue(getMyCareCalendar(apiResponse.getResult()));
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


    public List<CareCalendarResponse> getMyCareCalendar(@NonNull List<MySchedule> mySchedules) {

        // Sử dụng một Map để nhóm CareCalendar theo ngày chăm sóc
        Map<Date, List<CareCalendar>> careCalendarMap = new HashMap<>();

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        String selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);
        Date currentDate = DateUtils.stringToDate(selectedDate);

        for (MySchedule mySchedule : mySchedules) {
            Date startDate = mySchedule.getStartDate();
            Date endDate = mySchedule.getEndDate();
            int frequency = mySchedule.getFrequency();

            // Tính toán các ngày chăm sóc từ ngày bắt đầu đến ngày kết thúc với tần suất
            List<Date> scheduledDates = calculateScheduledDates(startDate, endDate, frequency);

            // Tạo CareCalendar cho mỗi ngày chăm sóc và thêm vào Map
            for (Date date : scheduledDates) {
                if(!date.before(currentDate)) {
                    List<CareCalendar> careCalendars = careCalendarMap.getOrDefault(date, new ArrayList<>());

                    CareCalendar careCalendar = new CareCalendar();
                    careCalendar.setMyScheduleId(mySchedule.getId());
                    careCalendar.setName(mySchedule.getName());
                    careCalendar.setStartDate(startDate);
                    careCalendar.setEndDate(endDate);
                    careCalendar.setTime(mySchedule.getTime());
                    careCalendar.setFrequency(frequency);

                    careCalendars.add(careCalendar);
                    careCalendarMap.put(date, careCalendars);
                }
            }
        }

        boolean checkToDay = false;
        // Tạo danh sách MyCareCalendarResponse từ Map
        List<CareCalendarResponse> calendarResponses = new ArrayList<>();
        for (Map.Entry<Date, List<CareCalendar>> entry : careCalendarMap.entrySet()) {
            Date date = entry.getKey();

            Calendar calendarTest = Calendar.getInstance();
            calendarTest.setTime(date);
            //Log.e("Test2", DateUtils.formatToDDMMYYYY(date) + " " +(DateUtils.formatToDDMMYYYY(currentDate)));
            if(date.compareTo(currentDate) == 0) checkToDay = true;

            List<CareCalendar> careCalendars = entry.getValue();

            CareCalendarResponse calendarResponse = new CareCalendarResponse();
            calendarResponse.setDateCare(date);
            calendarResponse.setCareCalendars(careCalendars);

            calendarResponses.add(calendarResponse);
        }
        if(!checkToDay) {
            calendarResponses.add(new CareCalendarResponse(currentDate, null));
        }
        Collections.sort(calendarResponses);

        return calendarResponses;
    }

    // Phương thức để tính toán các ngày chăm sóc từ ngày bắt đầu đến ngày kết thúc với tần suất
    @NonNull
    private List<Date> calculateScheduledDates(Date startDate, Date endDate, int frequency) {
        List<Date> scheduledDates = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        while (!calendar.getTime().after(endDate)) {
            java.util.Date setUtilDate = calendar.getTime();
            Date sqlDate = new Date(setUtilDate.getTime());
            scheduledDates.add(new Date(sqlDate.getTime()));
            calendar.add(Calendar.DAY_OF_MONTH, frequency);
        }

        return scheduledDates;
    }


}
