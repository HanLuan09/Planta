package vn.edu.ptit.planta.ui.register;

import android.content.Intent;
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
                tilUsername = (TextInputLayout) findViewById(R.id.til_username);
                tilPassword = (TextInputLayout) findViewById(R.id.til_password);
                tilConfirmPassword = (TextInputLayout) findViewById(R.id.til_confirm_password);

                tietUsername = (TextInputEditText) tilUsername.getEditText();
                tietPassword = (TextInputEditText) tilPassword.getEditText();
                tietConfirmPassword = (TextInputEditText) tilConfirmPassword.getEditText();

                String username = tietUsername.getText().toString();
                String password = tietPassword.getText().toString();
                String confirmPassword = tietConfirmPassword.getText().toString();

                if(checkPassword(password, confirmPassword)){
                    User userSend = new User();
                    userSend.setUsername(username);
                    userSend.setPassword(password);
                    Log.e("Start send", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                    if (register(userSend)){
                        Log.e("Send success", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                else{
                    Toast.makeText(RegisterActivity.this, "Please confirm the correct password!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private Boolean checkPassword(String password, String confirmPassword){
        boolean checkPassword = true;
        return checkPassword;
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
    public boolean register(User userSend){
        RetrofitClient.getUserService().register(userSend).enqueue(new Callback<ApiResponse<UserResponse>>() {
            @Override
            public void onResponse(Call<ApiResponse<UserResponse>> call, Response<ApiResponse<UserResponse>> response) {
                if(response.isSuccessful()) {
                    check = true;
                    ApiResponse<UserResponse> apiResponse = response.body();
                    UserResponse userResponse = apiResponse.getResult();
                    String message = apiResponse.getMessage();
                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                }
                else{
                    ApiResponse<UserResponse> apiResponse = response.body();
                    String message = apiResponse.getMessage();
                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<UserResponse>> call, Throwable throwable) {
                check = false;
            }
        });
        return check;
    }
}
