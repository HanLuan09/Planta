package vn.edu.ptit.planta.utils;

import androidx.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class DateUtils {
    @NonNull
    public static String formatToDDMMYYYY(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfOutput = new SimpleDateFormat("dd-MM-yyyy");
        try {
            return sdfOutput.format(sdfInput.parse(date.toString()));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static Date stringToDate(String date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date utilDate = dateFormat.parse(date);
            Date sqlDate = new Date(utilDate.getTime());
            return sqlDate;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static long diffDays(@NonNull Date startDate, @NonNull Date currentDate) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTimeInMillis(startDate.getTime());

        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTimeInMillis(currentDate.getTime());

        long startTimeInMillis = startCalendar.getTimeInMillis();
        long currentTimeInMillis = currentCalendar.getTimeInMillis();

        // Số lượng mili giây trong một ngày
        long millisecondsPerDay = 24 * 60 * 60 * 1000;

        return Math.abs((currentTimeInMillis - startTimeInMillis) / millisecondsPerDay);
    }

    public static long daysBetweenTodayAndStartDate(String startDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.set(Calendar.HOUR_OF_DAY, 0);
        todayCalendar.set(Calendar.MINUTE, 0);
        todayCalendar.set(Calendar.SECOND, 0);
        todayCalendar.set(Calendar.MILLISECOND, 0);
        java.util.Date todayDate = todayCalendar.getTime();

        java.util.Date startDateDate = null;
        Calendar startCalendar = Calendar.getInstance();
        try {
            startDateDate = sdf.parse(startDate);
            startCalendar.setTime(startDateDate);
            startCalendar.set(Calendar.HOUR_OF_DAY, 0);
            startCalendar.set(Calendar.MINUTE, 0);
            startCalendar.set(Calendar.SECOND, 0);
            startCalendar.set(Calendar.MILLISECOND, 0);
            startDateDate = startCalendar.getTime();

            long diffInMillis = todayDate.getTime() - startDateDate.getTime();
            return diffInMillis / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            return -1;
        }
    }

}

