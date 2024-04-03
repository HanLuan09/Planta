package vn.edu.ptit.planta.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

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
    private TextView tvRegister;
    private Toolbar toolbar;
    private Button btnLogin;
    private TextInputLayout tilUsername, tilPassword;
    private TextInputEditText tietUsername, tietPassword;
    private boolean check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toRegisterView();
        back();

        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLogin.setEnabled(false);

                tilUsername = (TextInputLayout) findViewById(R.id.til_username);
                tilPassword = (TextInputLayout) findViewById(R.id.til_password);

                tietUsername = (TextInputEditText) tilUsername.getEditText();
                tietPassword = (TextInputEditText) tilPassword.getEditText();

                User userSend = new User();
                userSend.setUsername(tietUsername.getText().toString());
                userSend.setPassword(tietPassword.getText().toString());

                Log.e("Start send", "Start send >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

                checkLogin(userSend);
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
    public void checkLogin(User userSend){
        RetrofitClient.getUserService().login(userSend).enqueue(new Callback<ApiResponse<UserResponse>>() {
            @Override
            public void onResponse(Call<ApiResponse<UserResponse>> call, Response<ApiResponse<UserResponse>> response) {
                if(response.isSuccessful()) {
                    check = true;
                    ApiResponse<UserResponse> apiResponse = response.body();
                    if(apiResponse.isSuccess()) {
                        UserResponse userResponse = apiResponse.getResult();
                        String message = apiResponse.getMessage();
                        loginSuccess(message);
                    }
                    else {
                        String message = apiResponse.getMessage();
                        loginFail(message);
                    }
                }
                else{
                    String message = "Data is empty!";
                    responseFail(message);
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<UserResponse>> call, Throwable throwable) {
                String message = "Fail connect to API";
                connectFail(message);
            }
        });
    }
    private void loginSuccess(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void loginFail(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
    }
    private void responseFail(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
    }
    private void connectFail(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
