package vn.edu.ptit.planta.data.service;

import kotlin.ParameterName;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import vn.edu.ptit.planta.model.User;

public interface UserService {

    @POST("user/login")
    Call<User> getUser(@Body User user);
}
