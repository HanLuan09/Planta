package vn.edu.ptit.planta.data.service;

import kotlin.ParameterName;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import vn.edu.ptit.planta.model.ApiResponse;
import vn.edu.ptit.planta.model.User;
import vn.edu.ptit.planta.model.UserResponse;

public interface UserService {

    @POST("user/login")
    Call<ApiResponse<UserResponse>> login(@Body User user);

    @POST("user/register")
    Call<ApiResponse<UserResponse>> register(@Body User user);
}
