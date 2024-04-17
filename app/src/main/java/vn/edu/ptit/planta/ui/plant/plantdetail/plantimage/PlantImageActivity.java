package vn.edu.ptit.planta.ui.plant.plantdetail.plantimage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import vn.edu.ptit.planta.databinding.ActivityPlantImageBinding;
import vn.edu.ptit.planta.model.plant.PlantImage;

public class PlantImageActivity extends AppCompatActivity {

    private ActivityPlantImageBinding binding;

    private PlantImagePageAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlantImageBinding.inflate(getLayoutInflater());
        binding.setLifecycleOwner(this);
        setContentView(binding.getRoot());

        List<PlantImage> receivedPlantImages = (List<PlantImage>) getIntent().getSerializableExtra("plant_images");

        if (receivedPlantImages != null && !receivedPlantImages.isEmpty()) {
            initImage(receivedPlantImages);
        }
        binding.closeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initImage(@NonNull List<PlantImage> images) {
        ViewPager2 viewPager2 = binding.plantImageViewpager;
        TextView tvCurren = binding.tv1;
        TextView tvTotal = binding.tv2;
        ImageButton tvBack = binding.ibBack;
        ImageButton tvNext = binding.ibNext;

        adapter = new PlantImagePageAdapter(this, images);
        viewPager2.setAdapter(adapter);

        tvCurren.setText("1");
        tvTotal.setText(String.valueOf(images.size()));
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvCurren.setText(String.valueOf(position + 1));

                if (position == 0) {
                    tvBack.setEnabled(false);  // Disable "Back" button at first page
                    tvNext.setEnabled(true);   // Enable "Next" button
                } else if (position == images.size() - 1) {
                    tvBack.setEnabled(true);
                    tvNext.setEnabled(false);
                } else {
                    tvBack.setEnabled(true);
                    tvNext.setEnabled(true);
                }
            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = viewPager2.getCurrentItem();
                if (currentPosition > 0) {
                    viewPager2.setCurrentItem(currentPosition - 1);
                }
            }
        });

        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = viewPager2.getCurrentItem();
                if (currentPosition < images.size() - 1) {
                    viewPager2.setCurrentItem(currentPosition + 1);
                }
            }
        });


    }

}