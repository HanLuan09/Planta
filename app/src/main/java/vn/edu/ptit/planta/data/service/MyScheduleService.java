package vn.edu.ptit.planta.data.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import vn.edu.ptit.planta.model.myschedule.MySchedule;

public interface MyScheduleService {
    @GET("schedule/plant/{id}")
    Call<List<MySchedule>> myScheduleByPlant(@Path("id") int idMyPlant);
}
