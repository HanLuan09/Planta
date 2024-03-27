package vn.edu.ptit.planta.data.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import vn.edu.ptit.planta.model.User;

public interface UserService {

    @POST("user/login")
    Call<User> getUser(@Body User user);
}
