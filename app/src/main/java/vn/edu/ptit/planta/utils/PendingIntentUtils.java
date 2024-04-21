package vn.edu.ptit.planta.utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PendingIntentUtils {
    private static final String KEY_SHARED = "notificationSharedPreferences";
    public static void savePendingIntentList(@NonNull Context context, List<PendingIntent> pendingIntentList) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_SHARED, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(pendingIntentList);

        String key = "pendingIntent";
        editor.putString(key, json);
        editor.apply();
    }

    // Khôi phục danh sách PendingIntent từ SharedPreferences
    @NonNull
    public static List<PendingIntent> getPendingIntentList(@NonNull Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_SHARED, Context.MODE_PRIVATE);

        String key = "pendingIntent";
        String json = sharedPreferences.getString(key, "");
        Gson gson = new Gson();

        Type listType = new TypeToken<List<PendingIntent>>() {}.getType();
        List<PendingIntent> pendingIntentList = gson.fromJson(json, listType);


        if (pendingIntentList == null) {
            pendingIntentList = new ArrayList<>();
        }

        return pendingIntentList;
    }
    public static void removePendingIntentAll(@NonNull Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_SHARED, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();
        editor.apply();
    }
}