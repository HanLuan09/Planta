package vn.edu.ptit.planta.utils;

import androidx.annotation.NonNull;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Time;

public class TimeUtils {
    @NonNull
    public static String formatToHHMM(Time time) {
        if (time == null) {
            return "";
        }
        SimpleDateFormat sdfOutput = new SimpleDateFormat("HH:mm");
        return sdfOutput.format(time);
    }

    @NonNull
    public static String formatToHHMMSS(Time time) {
        if (time == null) {
            return "";
        }
        SimpleDateFormat sdfOutput = new SimpleDateFormat("HH:mm:ss");
        return sdfOutput.format(time);
    }

    public static Time stringToTime(String time) {
        if (time == null) {
            return null;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        try {
            java.util.Date utilDate = dateFormat.parse(time);
            long timeInMillis = utilDate.getTime();
            Time sqlTime = new Time(timeInMillis);
            return sqlTime;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
