package vn.edu.ptit.planta.ui.guesthome;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.ui.login.LoginActivity;
import vn.edu.ptit.planta.ui.register.RegisterActivity;

public class GuestHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_home);

        Button button = findViewById(R.id.id_guest_button_login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GuestHomeActivity.this, LoginActivity.class);
                startActivity(intent);
                GuestHomeActivity.this.overridePendingTransition(0, 0);
            }
        });
    }
}