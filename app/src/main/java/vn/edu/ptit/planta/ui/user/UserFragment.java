package vn.edu.ptit.planta.ui.user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.data.RetrofitClient;
import vn.edu.ptit.planta.model.ApiResponse;
import vn.edu.ptit.planta.model.UserResponse;
import vn.edu.ptit.planta.ui.user.profile.ProfileActivity;


public class UserFragment extends Fragment {
    private TextView tvFullnameUser, tvPhoneUser;
    private RelativeLayout btn_profile;
    private UserResponse userResponse;
    private int idUser;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("User", Context.MODE_PRIVATE);
        idUser = sharedPreferences.getInt("idUser",0);

        tvFullnameUser = view.findViewById(R.id.tv_fullName_user);
        tvPhoneUser = view.findViewById(R.id.tv_phone_user);

        getUser();

        btn_profile = view.findViewById(R.id.btn_profile);
        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void getUser() {
        RetrofitClient.getUserService().getUser(idUser).enqueue(new Callback<ApiResponse<UserResponse>>() {
            @Override
            public void onResponse(Call<ApiResponse<UserResponse>> call, Response<ApiResponse<UserResponse>> response) {
                if(response.isSuccessful()){
                    ApiResponse<UserResponse> apiResponse = response.body();
                    if(apiResponse.isSuccess()){
                        userResponse = apiResponse.getResult();
                        initData();
                    } else {
                        String message = apiResponse.getMessage();
                        responseFalse(message);
                    }
                } else {
                    String message = "Phản hồi không hợp lệ";
                    responseInvalid(message);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<UserResponse>> call, Throwable throwable) {
                String message = " Kết nối tới server thất bại!";
                connectFail(message);
            }
        });
    }

    private void initData() {
        if(userResponse!=null){
            if(userResponse.getName() == null){
                tvFullnameUser.setText("Người dùng " + idUser);
            } else {
                tvFullnameUser.setText(userResponse.getName());
            }
            if(userResponse.getPhone() == null){
                tvPhoneUser.setText("Chưa có sđt");
            } else {
                tvPhoneUser.setText(userResponse.getPhone());
            }
        }
    }

    private void responseFalse(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void responseInvalid(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void connectFail(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}

