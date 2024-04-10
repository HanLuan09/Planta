package vn.edu.ptit.planta.data.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import vn.edu.ptit.planta.model.ApiResponse;
import vn.edu.ptit.planta.model.care.CareScheduleResponse;
import vn.edu.ptit.planta.model.myplant.MyPlant;
import vn.edu.ptit.planta.model.myplant.MyPlantScheduleResponse;

public interface MyPlantService {

    @GET("my_plant/{idUser}/all")
    Call<ApiResponse<List<MyPlant>>> getAllMyPlant(@Path("idUser") int id);

    @GET("my_plant/{idUser}/{idMyPlant}")
    Call<ApiResponse<MyPlant>> getMyPlant(@Path("idUser") int id, @Path("idMyPlant") int idMyPlant);

    @GET("my_plant/schedule/{id}")
    Call<ApiResponse<List<MyPlantScheduleResponse>>> getMyPlantScheduleByUser(@Path("id") int userId);

    @GET("my_plant/calendar/{id}")
    Call<ApiResponse<List<MyPlantScheduleResponse>>> getAllMyPlantCalendarByUser(@Path("id") int userId);
}
