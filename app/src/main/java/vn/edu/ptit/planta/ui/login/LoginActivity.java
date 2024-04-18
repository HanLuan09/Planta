package vn.edu.ptit.planta.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

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
import vn.edu.ptit.planta.ui.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity {
    private UserViewModel userViewModel;
    private TextView tvRegister;
    private Toolbar toolbar;
    private Button btnLogin;
    private TextInputLayout tilUsername, tilPassword;
    private TextInputEditText tietUsername, tietPassword;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        toRegisterView();
        back();

        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLogin.setEnabled(false);

                progressBar = findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);
                btnLogin.setText(null);

                tilUsername = findViewById(R.id.til_username);
                tilPassword = findViewById(R.id.til_password);

                tietUsername = (TextInputEditText) tilUsername.getEditText();
                tietPassword = (TextInputEditText) tilPassword.getEditText();

                String username = tietUsername.getText().toString();
                String password = tietPassword.getText().toString();

                if(checkField(username, password)) {

                    User userSend = new User();
                    userSend.setUsername(username);
                    userSend.setPassword(password);

                    checkLogin(userSend);
                }

                btnLogin.setText("Đăng nhập");
                btnLogin.setEnabled(true);
            }
        });
    }

    private void toRegisterView() {
        tvRegister = findViewById(R.id.tv_login_to_signup);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    private boolean checkField(String username, String password) {

        if(username == null && password == null){
            Toast.makeText(this,"Vui lòng nhập đầy đủ tài khoản và mật khẩu!", Toast.LENGTH_SHORT);
            return false;
        } else if (username == null) {
            Toast.makeText(this,"Vui lòng nhập tài khoản!", Toast.LENGTH_SHORT);
            return false;
        } else if (password == null) {
            Toast.makeText(this,"Vui lòng nhập mật khẩu!", Toast.LENGTH_SHORT);
            return false;
        }
        else{
            return  true;
        }
    }
    public void checkLogin(User userSend){
        RetrofitClient.getUserService().login(userSend).enqueue(new Callback<ApiResponse<UserResponse>>() {
            @Override
            public void onResponse(Call<ApiResponse<UserResponse>> call, Response<ApiResponse<UserResponse>> response) {
                if(response.isSuccessful()) {
                    ApiResponse<UserResponse> apiResponse = response.body();
                    if(apiResponse.isSuccess()) {
                        UserResponse userResponse = apiResponse.getResult();
                        String message = apiResponse.getMessage();
                        loginSuccess(message, userResponse);
                    }
                    else {
                        String message = apiResponse.getMessage();
                        loginFail(message);
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
    private void loginSuccess(String message, UserResponse userResponse) {

        SharedPreferences sharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("idUser", userResponse.getId());
        editor.apply();

        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void loginFail(String message) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
    }
    private void responseFail(String message) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
    }
    private void connectFail(String message) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
    }
    private void back(){
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
