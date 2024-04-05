package vn.edu.ptit.planta.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.data.RetrofitClient;
import vn.edu.ptit.planta.model.User;
import vn.edu.ptit.planta.ui.MainActivity;
import vn.edu.ptit.planta.ui.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity {
    private TextView tvRegister;
    private Toolbar toolbar;
    private Button btnLogin;
    private EditText edUsername, edPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toRegisterPage();

        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                edUsername = findViewById(R.id.edUsername);
//                edPassword = findViewById(R.id.edPassword);
//                User userSend = new User();
//                userSend.setUsername(edUsername.getText().toString());
//                userSend.setPassword(edPassword.getText().toString());
//                checkLogin(userSend);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
//                finish();
            }
        });
    }

    private void toRegisterPage() {
        tvRegister = findViewById(R.id.tv_login_to_sigup);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public void checkLogin(User userSend){
        RetrofitClient.getUserService().getUser(userSend).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    User user = response.body();
                    //kiem tra user
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {
                Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                Log.e("API ERROR", throwable.getMessage(), throwable);
            }
        });
    }
}
