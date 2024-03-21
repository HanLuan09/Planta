package vn.edu.ptit.planta.ui.guesthome;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.databinding.ActivityGuestHomeBinding;
import vn.edu.ptit.planta.databinding.ActivityMyPlantDetailBinding;
import vn.edu.ptit.planta.ui.login.LoginActivity;
import vn.edu.ptit.planta.ui.register.RegisterActivity;

public class GuestHomeActivity extends AppCompatActivity {

    private ActivityGuestHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        binding = ActivityGuestHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.idGuestButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GuestHomeActivity.this, LoginActivity.class);
                startActivity(intent);
                GuestHomeActivity.this.overridePendingTransition(0, 0);
                finish();
            }
        });

        Glide.with(this)
                .load(R.drawable.face)
                .into(binding.ivGuestHome);
    }
}