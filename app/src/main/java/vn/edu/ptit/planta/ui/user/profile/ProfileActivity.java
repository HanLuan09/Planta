package vn.edu.ptit.planta.ui.user.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.data.RetrofitClient;
import vn.edu.ptit.planta.model.ApiResponse;
import vn.edu.ptit.planta.model.UserResponse;
import vn.edu.ptit.planta.ui.MainActivity;
import vn.edu.ptit.planta.ui.user.UserFragment;

public class ProfileActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Switch switchEnable;
    private EditText etName, etPhone, etEmail, etAddress;
    private Button btnSave;
    private ProgressBar progressBar;
    private UserResponse userResponse, userRequest;
    private int idUser;
    private boolean enable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        SharedPreferences sharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE);
        idUser = sharedPreferences.getInt("idUser",0);

        switchEnable = findViewById(R.id.switch_enable);
        etName = findViewById(R.id.et_content_user_name);
        etPhone = findViewById(R.id.et_content_user_phone);
        etEmail = findViewById(R.id.et_content_user_email);
        etAddress = findViewById(R.id.et_content_user_address);
        btnSave = findViewById(R.id.btn_save);

        getUser();

        unEnableEdit();
        switchEnable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enable = switchEnable.isChecked();
                if(enable){
                    enableEdit();
                } else {
                    unEnableEdit();
                }
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkUserRequest()){
                    btnSave.setEnabled(false);

                    progressBar = findViewById(R.id.progressBar);
                    progressBar.setVisibility(View.VISIBLE);
                    updateUser();
                    btnSave.setEnabled(true);
                }
            }
        });
        back();
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

                }
            }

            @Override
            public void onFailure(Call<ApiResponse<UserResponse>> call, Throwable throwable) {
                String message = " Kết nối tới server thất bại!";
                connectFail(message);
            }
        });
    }

    private boolean checkUserRequest() {
        String name = etName.getText().toString();
        String phone = etPhone.getText().toString();
        String email = etEmail.getText().toString();
        String address = etAddress.getText().toString();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Vui lòng nhập tên người dùng!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(TextUtils.isEmpty(phone)){
            Toast.makeText(this, "Vui lòng nhập số điện thoại!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Vui lòng nhập email!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(TextUtils.isEmpty(address)){
            Toast.makeText(this, "Vui lòng nhập địa chỉ!", Toast.LENGTH_SHORT).show();
            return false;
        }

        userRequest = new UserResponse();
        userRequest.setId(idUser);
        userRequest.setName(name);
        userRequest.setPhone(phone);
        userRequest.setEmail(email);
        userRequest.setAddress(address);

        return true;
    }

    private void updateUser() {
        RetrofitClient.getUserService().updateInformation(userRequest).enqueue(new Callback<ApiResponse<UserResponse>>() {
            @Override
            public void onResponse(Call<ApiResponse<UserResponse>> call, Response<ApiResponse<UserResponse>> response) {
                if(response.isSuccessful()){
                    ApiResponse<UserResponse> apiResponse = response.body();
                    if(apiResponse.isSuccess()){
                        String message = apiResponse.getMessage();
                        responseTrue(message);
                    } else {
                        String message = apiResponse.getMessage();
                        responseFalse(message);
                    }

                } else {
                    String message = "Phản hồi không hợp lệ!";
                    responseInvalid(message);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<UserResponse>> call, Throwable throwable) {
                String message = "Kết nối tới server thất bại!";
                connectFail(message);
            }
        });
    }

    private void initData() {
        if(userResponse!=null){
            if(userResponse.getName() != null){
                etName.setText(userResponse.getName());
            }
            if(userResponse.getPhone() != null){
                etPhone.setText(userResponse.getPhone());
            }
            if(userResponse.getEmail() != null){
                etEmail.setText(userResponse.getEmail());
            }
            if(userResponse.getAddress() != null){
                etAddress.setText(userResponse.getAddress());
            }
        }
    }

    private void enableEdit() {
        etName.setEnabled(true);
        etPhone.setEnabled(true);
        etEmail.setEnabled(true);
        etAddress.setEnabled(true);
        btnSave.setEnabled(true);
    }

    private void unEnableEdit() {
        etName.setEnabled(false);
        etPhone.setEnabled(false);
        etEmail.setEnabled(false);
        etAddress.setEnabled(false);
        btnSave.setEnabled(false);
    }

    public void back(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
        toolbar.getNavigationIcon().setTint(getResources().getColor(R.color.colorGreenText));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void responseTrue(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
//        startActivity(intent);
    }

    private void responseFalse(String message) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void responseInvalid(String message) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void connectFail(String message) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
