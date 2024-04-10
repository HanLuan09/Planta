package vn.edu.ptit.planta.ui.plant.plantdetail;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import vn.edu.ptit.planta.databinding.ActivityPlantDetailBinding;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.model.plant.Plant;
import vn.edu.ptit.planta.model.plant.PlantDetail;

public class PlantDetailActivity extends AppCompatActivity {

    private ActivityPlantDetailBinding binding;

    private PlantDetailViewModel viewModel;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPlantDetailBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(PlantDetailViewModel.class);
        binding.setPlantDetailViewModel(viewModel);
        binding.setLifecycleOwner(this);
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
        viewModel.getIdPlant().setValue(plant.getId());
        viewModel.initData();
        viewModel.getPlantDetail().observe(this, new Observer<PlantDetail>() {
            @Override
            public void onChanged(PlantDetail plantDetail) {
                binding.collapsingToolbar.setTitle(viewModel.getPlantDetail().getValue().getName());

                imageGlide(viewModel.getPlantDetail().getValue().getMainImage(), binding.idImgPlant);

                binding.tvContentNameMyplanta.setText(viewModel.getPlantDetail().getValue().getTypePlant());

                imageGlide(viewModel.getPlantDetail().getValue().getMainImage(), binding.imageViewOne);

                imageGlide(viewModel.getPlantDetail().getValue().getSecondaryImage(), binding.imageViewTwo);
            }
        });
    }

    private void imageGlide(String image, ShapeableImageView shapeableImageView){
        Glide.with(PlantDetailActivity.this)
                .load(image)
                .placeholder(R.drawable.icon_no_mob)
                .override(400,400)
                .into(shapeableImageView);
    }

}