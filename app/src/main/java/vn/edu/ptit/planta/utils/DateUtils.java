package vn.edu.ptit.planta.utils;

import androidx.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

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
}

