package vn.edu.ptit.planta.data.service;

import java.util.List;

import kotlin.ParameterName;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import vn.edu.ptit.planta.model.ApiResponse;
import vn.edu.ptit.planta.model.care.CareScheduleResponse;
import vn.edu.ptit.planta.model.myplant.MyPlant;
import vn.edu.ptit.planta.model.myplant.MyPlantRequest;
import vn.edu.ptit.planta.model.myplant.MyPlantScheduleResponse;

public interface MyPlantService {

    @GET("my_plant/{idUser}/all")
    Call<ApiResponse<List<MyPlant>>> getAllMyPlant(@Path("idUser") int id);

    @GET("my_plant/{idUser}/search")
    Call<ApiResponse<List<MyPlant>>> getAllMyPlantByKey(@Path("idUser") int id, @Query("key") String key);

    @GET("my_plant/{idUser}/{idMyPlant}")
    Call<ApiResponse<MyPlant>> getMyPlant(@Path("idUser") int id, @Path("idMyPlant") int idMyPlant);

    @POST("my_plant/{idUser}/add")
    Call<ApiResponse<MyPlantRequest>> addMyPlant(@Path("idUser") int id, @Body MyPlantRequest myPlantRequest);

    @PUT("my_plant/{idUser}/update/{idMyPlant}")
    Call<ApiResponse<Boolean>> updateMyPlant(@Path("idUser") int idUser, @Path("idMyPlant") int idMyPlant, @Body MyPlantRequest myPlantRequest);

    @DELETE("my_plant/delete/{idMyPlant}")
    Call<ApiResponse<Boolean>> deleteMyPlant(@Path("idMyPlant") int idMyPlant);

    @GET("my_plant/schedule/{id}")
    Call<ApiResponse<List<MyPlantScheduleResponse>>> getMyPlantScheduleByUser(@Path("id") int userId);

    @GET("my_plant/calendar/{id}")
    Call<ApiResponse<List<MyPlantScheduleResponse>>> getAllMyPlantCalendarByUser(@Path("id") int userId);
}
