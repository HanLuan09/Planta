package vn.edu.ptit.planta.data.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import vn.edu.ptit.planta.model.ApiResponse;
import vn.edu.ptit.planta.model.plant.Plant;
import vn.edu.ptit.planta.model.plant.PlantDetail;

public interface PlantService {
    @GET("plant/all")
    Call<ApiResponse<List<Plant>>> listPlants();

    @GET("plant/search")
    Call<ApiResponse<List<Plant>>> getPlantByName(@Query("key") String key);

    @GET("plant/page")
    Call<ApiResponse<List<Plant>>> listPlantPage(@Query("page") int page, @Query("limit") int limit);

    @GET("plant/{id}")
    Call<ApiResponse<PlantDetail>> plantDetail(@Path("id") int id);
}
