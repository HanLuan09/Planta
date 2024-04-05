package vn.edu.ptit.planta.data.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import vn.edu.ptit.planta.model.ApiResponse;
import vn.edu.ptit.planta.model.myplant.MyPlantScheduleResponse;
import vn.edu.ptit.planta.model.care.CareScheduleResponse;

public interface MyPlantService {

    @GET("my_plant/today/{id}")
    Call<ApiResponse<List<CareScheduleResponse>>> getMyPlantToDayByUser(@Path("id") int userId);

    @GET("my_plant/calendar/{id}")
    Call<ApiResponse<List<MyPlantScheduleResponse>>> getAllMyPlantCalendarByUser(@Path("id") int userId);
}
