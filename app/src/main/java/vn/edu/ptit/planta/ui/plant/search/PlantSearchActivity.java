package vn.edu.ptit.planta.ui.plant.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.databinding.ActivityPlantSearchBinding;
import vn.edu.ptit.planta.model.DataStatus;
import vn.edu.ptit.planta.model.plant.Plant;
import vn.edu.ptit.planta.ui.myplant.search.SearchMyPlantViewModel;

public class PlantSearchActivity extends AppCompatActivity {

    private ActivityPlantSearchBinding binding;
    private PlantSearchViewModel viewModel;
    private PlantSearchAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding =  DataBindingUtil.setContentView(this, R.layout.activity_plant_search);
        viewModel = new ViewModelProvider(this).get(PlantSearchViewModel.class);
        binding.setPlantSearchViewModel(viewModel);
        binding.setLifecycleOwner(this);

        Glide.with(this)
                .load(R.drawable.ic_loading)
                .into(binding.loading);
        binding.searchPlantBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = binding.rcvSearchPlant;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new PlantSearchAdapter(this);

        binding.btnSearchPlant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = viewModel.getTextSearch().getValue().trim();
                if(key == null || key.isEmpty()){

                }else {
                    viewModel.getDataStatus().setValue(new DataStatus(false, false,"Đang kết nối"));
                    viewModel.handleDataSearch(key);
                    viewModel.getListPlant().observe(PlantSearchActivity.this, new Observer<List<Plant>>() {
                        @Override
                        public void onChanged(List<Plant> plants) {
                            adapter.updateData(plants);
                            recyclerView.setAdapter(adapter);
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(adapter != null) adapter.release();
    }
}