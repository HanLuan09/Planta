package vn.edu.ptit.planta.data.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import vn.edu.ptit.planta.model.Plant;

public interface PlantService {
    @GET("plant/{id}")
    Call<List<Plant>> listRepos(@Path("id") String id);
}
