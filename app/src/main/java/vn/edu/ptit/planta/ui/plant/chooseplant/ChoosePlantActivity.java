package vn.edu.ptit.planta.ui.plant.chooseplant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import vn.edu.ptit.planta.R;

import vn.edu.ptit.planta.databinding.ActivityChoosePlantBinding;
import vn.edu.ptit.planta.model.plant.Plant;
import vn.edu.ptit.planta.ui.plant.plantdetail.PlantDetailActivity;
import vn.edu.ptit.planta.ui.myplant.addmyplant.AddMyPlantActivity;

public class ChoosePlantActivity extends AppCompatActivity implements ChoosePlantNavigator {
    private ActivityChoosePlantBinding binding;
    private RecyclerView rcvChoosePlant;
    private ChoosePlantAdapter choosePlantAdapter;
    private ChoosePlantViewModel choosePlantViewModel;
    private TextInputLayout tilSearchPlant;
    private TextInputEditText tietSearchPlant;
    private ImageView ivSearch;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_choose_plant);

        choosePlantViewModel = new ViewModelProvider(this).get(ChoosePlantViewModel.class);
        choosePlantViewModel.setChoosePlantNavigator(this);

        binding.setChoosePlantViewModel(choosePlantViewModel);
        binding.setLifecycleOwner(this);

        initRecycleView();
        ivSearch = findViewById(R.id.iv_search);
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tilSearchPlant = findViewById(R.id.til_search_plant);
                tietSearchPlant = (TextInputEditText) tilSearchPlant.getEditText();
                String key = tietSearchPlant.getText().toString();
                if(key.isEmpty()){
                    Toast.makeText(ChoosePlantActivity.this, "Vui lòng nhập từ khóa!", Toast.LENGTH_SHORT).show();
                } else {
                    choosePlantViewModel.setKeySearch(key);
                }
            }
        });
        initBundleSearch();
        back();
    }

    private void initRecycleView(){
        rcvChoosePlant = findViewById(R.id.rcv_choose_plant);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvChoosePlant.setLayoutManager(linearLayoutManager);

        // Quan sát dữ liệu thay đổi
        choosePlantViewModel.getListPlant().observe(ChoosePlantActivity.this, new Observer<List<Plant>>() {
            @Override
            public void onChanged(List<Plant> plants) {
                if(choosePlantAdapter == null){
                    // Nếu adapter chưa được tạo
                    choosePlantAdapter = new ChoosePlantAdapter(plants);
                    choosePlantAdapter.setChoosePlantNavigator(ChoosePlantActivity.this);
                    rcvChoosePlant.setAdapter(choosePlantAdapter);
                }
                else {
                    choosePlantAdapter.updateData(plants);
                }
            }
        });
    }
    private void initBundleSearch() {
        Bundle bundle = getIntent().getExtras();
        if(bundle == null) {
            choosePlantViewModel.getIsSearch().setValue(false);
            return;
        }
        choosePlantViewModel.getIsSearch().setValue(bundle.getBoolean("is_search"));
    }


    @Override
    public void handleBack() {
        finish();
    }

    @Override
    public void handleChoosePlant(Plant plant) {
        Intent intent;
        if (choosePlantViewModel.getIsSearch().getValue() == true) {
            intent = new Intent(this, PlantDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("plant", plant);
            intent.putExtras(bundle);
        } else {
            intent = new Intent(this, AddMyPlantActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("plant", plant);
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    public void glideImage(String image, ShapeableImageView shapeableImageView) {
        Glide.with(this).
                load(image).
                placeholder(R.drawable.icon_no_image).
                override(300,300).
                into(shapeableImageView);
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
