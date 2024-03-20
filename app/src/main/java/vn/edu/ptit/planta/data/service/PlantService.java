package vn.edu.ptit.planta.data.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import vn.edu.ptit.planta.model.Plant;
import vn.edu.ptit.planta.model.PlantDetail;

public interface PlantService {
    @GET("plant/all")
    Call<List<Plant>> listPlants();
    @GET("plant/{id}")
    Call<PlantDetail> plantDetail(@Path("id") int id);
}
