package vn.edu.ptit.planta.ui.splash;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import vn.edu.ptit.planta.ui.MainActivity;
import vn.edu.ptit.planta.ui.guesthome.GuestHomeActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);

        SharedPreferences sharedPreferences = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int idUser = sharedPreferences.getInt("id",-1);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(idUser == -1) {// chưa đăng nhập
                    Intent intent = new Intent(SplashActivity.this, GuestHomeActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                SplashActivity.this.overridePendingTransition(0, 0);
                finish();
            }
        }, 3000);
    }
}