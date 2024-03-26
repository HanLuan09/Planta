package vn.edu.ptit.planta.data;

import androidx.annotation.NonNull;

import retrofit2.Retrofit;

import retrofit2.converter.gson.GsonConverterFactory;
import vn.edu.ptit.planta.data.service.MyScheduleService;
import vn.edu.ptit.planta.data.service.PlantService;


public class RetrofitClient {

//    private static final String BASE_URL = "http://192.168.110.140:8080/api/";
    private static final String BASE_URL = "http://192.168.43.134:8080/api/";

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @NonNull
    public static PlantService getPlantService() {
        return retrofit.create(PlantService.class);
    }

    @NonNull
    public static MyScheduleService getMyScheduleService() {
        return retrofit.create(MyScheduleService.class);
    }
}

