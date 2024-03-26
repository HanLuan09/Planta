package vn.edu.ptit.planta.utils;

import androidx.annotation.NonNull;

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
}
