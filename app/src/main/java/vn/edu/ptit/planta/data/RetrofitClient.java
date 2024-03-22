package vn.edu.ptit.planta.data;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import retrofit2.Retrofit;

import retrofit2.converter.gson.GsonConverterFactory;
import vn.edu.ptit.planta.data.service.PlantService;
import vn.edu.ptit.planta.data.service.UserService;


public class RetrofitClient {

    private static final String BASE_URL = "http://192.168.125.222:8080/api/";

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @NonNull
    public static PlantService getPlantService() {
        return retrofit.create(PlantService.class);
    }

    @NonNull
    public static UserService getUserService() {
        return retrofit.create(UserService.class);
    }
}

