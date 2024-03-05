package vn.edu.ptit.planta.ui.plant.plantdetail;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.bumptech.glide.Glide;

import vn.edu.ptit.planta.databinding.ActivityPlantDetailBinding;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.model.Plant;

public class PlantDetailActivity extends AppCompatActivity {

    private ActivityPlantDetailBinding binding;

    private PlantDetailViewModel viewModel;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPlantDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setTint(getResources().getColor(R.color.colorGreenText));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        init();

    }
    private void init() {
        Bundle bundle = getIntent().getExtras();
        if(bundle == null) return;
        Plant plant = (Plant) bundle.get("plant");

        binding.collapsingToolbar.setTitle(plant.getName());

        Glide.with(this)
                .load(plant.getImage())
                .override(400,400)
                .into(binding.idImgPlant);
    }
}