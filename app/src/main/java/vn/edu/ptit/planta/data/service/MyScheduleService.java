package vn.edu.ptit.planta.data.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import vn.edu.ptit.planta.model.ApiResponse;
import vn.edu.ptit.planta.model.care.CareCalendarResponse;
import vn.edu.ptit.planta.model.myschedule.MySchedule;
import vn.edu.ptit.planta.model.myschedule.MyScheduleRequest;

public interface MyScheduleService {
    @GET("schedule/plant/{id}")
    Call<ApiResponse<List<MySchedule>>> myScheduleByPlant(@Path("id") int idMyPlant);

    @POST("schedule")
    Call<ApiResponse<MySchedule>> createMyScheduleByPlant(@Body MyScheduleRequest request);

    @PUT("schedule/{id}")
    Call<ApiResponse<MySchedule>> updateMyScheduleByPlant(@Path("id") int idMySchedule, @Body MyScheduleRequest request);

    @DELETE("schedule/{id}")
    Call<ApiResponse> deleteMySchedule(@Path("id") int idMySchedule);

    @GET("schedule/care/{id}")
    Call<ApiResponse<List<CareCalendarResponse>>> getMyCareCalendarByMyPlantId(@Path("id") int myPlantId);
}
