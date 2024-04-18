package vn.edu.ptit.planta.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import vn.edu.ptit.planta.model.care.CareCalendar;
import vn.edu.ptit.planta.model.care.CareCalendarResponse;
import vn.edu.ptit.planta.model.care.CareSchedule;
import vn.edu.ptit.planta.model.care.CareScheduleResponse;
import vn.edu.ptit.planta.model.myplant.MyPlantScheduleResponse;
import vn.edu.ptit.planta.model.myschedule.MySchedule;

public class MyPlantCalendarUtils {

    // trang calendar and today
    @Nullable
    public static List<CareScheduleResponse> myPlantCalendar(@NonNull List<MyPlantScheduleResponse> myPlants, String dateCalendar) {

        if(myPlants == null ) return null;

        Date currentDate = DateUtils.stringToDate(dateCalendar);

        Map<String, List<CareSchedule>> groupedSchedules = new HashMap<>();

        // Nhóm các Schedule theo tên bài tập
        for (MyPlantScheduleResponse myPlant : myPlants) {
            List<MySchedule> mySchedules = myPlant.getMySchedules();
            for (MySchedule mySchedule : mySchedules) {

                if ((!currentDate.before(mySchedule.getStartDate()) && (currentDate.before(mySchedule.getEndDate())))) {

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


    // trang care
    @NonNull
    public static List<CareCalendarResponse> myCareCalendar(@NonNull List<MySchedule> mySchedules) {

        // Map để nhóm Schedule theo ngày chăm sóc
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

            // Tạo Schedule cho mỗi ngày chăm sóc
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
    private static List<Date> calculateScheduledDates(Date startDate, Date endDate, int frequency) {
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
