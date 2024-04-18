package vn.edu.ptit.planta.ui.register;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.data.RetrofitClient;
import vn.edu.ptit.planta.model.ApiResponse;
import vn.edu.ptit.planta.model.User;
import vn.edu.ptit.planta.model.UserResponse;
import vn.edu.ptit.planta.ui.MainActivity;
import vn.edu.ptit.planta.ui.login.LoginActivity;

public class RegisterActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView tvLogin;
    private TextInputLayout tilUsername, tilPassword, tilConfirmPassword;
    private TextInputEditText tietUsername, tietPassword, tietConfirmPassword;
    private Button btnRegister;
    private boolean check;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        toLoginView();
        back();

        btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnRegister.setEnabled(false);

                tilUsername = findViewById(R.id.til_username);
                tilPassword = findViewById(R.id.til_password);
                tilConfirmPassword = findViewById(R.id.til_confirm_password);

                tietUsername = (TextInputEditText) tilUsername.getEditText();
                tietPassword = (TextInputEditText) tilPassword.getEditText();
                tietConfirmPassword = (TextInputEditText) tilConfirmPassword.getEditText();

                String username = tietUsername.getText().toString();
                String password = tietPassword.getText().toString();
                String confirmPassword = tietConfirmPassword.getText().toString();

                if(checkField(username, password, confirmPassword)){
                    User userSend = new User();
                    userSend.setUsername(username);
                    userSend.setPassword(password);

                    register(userSend);
                }
                btnRegister.setEnabled(true);
            }
        });
    }
    private void toLoginView() {
        tvLogin = findViewById(R.id.tv_signup_to_login);
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    private boolean checkField(String username, String password, String confirmPassword) {
        if(username.isEmpty() && password.isEmpty() && confirmPassword.isEmpty()){
            Toast.makeText(this,"Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (username.isEmpty()) {
            Toast.makeText(this,"Vui lòng nhập tài khoản!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.isEmpty()) {
            Toast.makeText(this,"Vui lòng nhập mật khẩu!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!checkPassword(password)) {
            Toast.makeText(RegisterActivity.this, "Vui lòng nhập mật khẩu tối thiếu 6 ký tự!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!checkDuplicatePassword(password, confirmPassword)) {
            Toast.makeText(RegisterActivity.this, "Vui lòng nhập lại đúng mật khẩu!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return  true;
    }
    private Boolean checkPassword(String password){
        if(password.length() < 6)
            return false;
        return true;
    }
    private Boolean checkDuplicatePassword(String password, String confirmPassword){
        if(password.equals(confirmPassword))
            return true;
        return false;
    }
    public void register(User userSend){
        RetrofitClient.getUserService().register(userSend).enqueue(new Callback<ApiResponse<UserResponse>>() {
            @Override
            public void onResponse(Call<ApiResponse<UserResponse>> call, Response<ApiResponse<UserResponse>> response) {
                if(response.isSuccessful()) {
                    check = true;
                    ApiResponse<UserResponse> apiResponse = response.body();
                    if(apiResponse.isSuccess()) {
                        UserResponse userResponse = apiResponse.getResult();
                        String message = apiResponse.getMessage();
                        registerSuccess(message, userResponse);
                    }
                    else {
                        String message = apiResponse.getMessage();
                        registerFail(message);
                    }
                }
                else{
                    String message = "Phản hồi không hợp lệ!";
                    responseFail(message);
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<UserResponse>> call, Throwable throwable) {
                String message = "Kết nối thất bại tới server!";
                connectFail(message);
            }
        });
    }
    private void registerSuccess(String message, UserResponse userResponse) {

        SharedPreferences sharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("idUser", userResponse.getId());
        editor.apply();

        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void registerFail(String message) {
        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
    }
    private void responseFail(String message) {
        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
    }
    private void connectFail(String message) {
        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
    }
    public void back(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
        toolbar.getNavigationIcon().setTint(getResources().getColor(R.color.colorGreenText));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
