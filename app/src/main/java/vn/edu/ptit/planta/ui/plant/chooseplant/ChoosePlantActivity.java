package vn.edu.ptit.planta.ui.plant.chooseplant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.databinding.ActivityChooseMyPlantBinding;
import vn.edu.ptit.planta.model.plant.Plant;
import vn.edu.ptit.planta.ui.plant.plantdetail.PlantDetailActivity;
import vn.edu.ptit.planta.ui.myplant.addmyplant.AddMyPlantActivity;

public class ChoosePlantActivity extends AppCompatActivity implements ChoosePlantNavigator {
    private ActivityChooseMyPlantBinding binding;
    private RecyclerView rcvAddMyPlant;
    private ChoosePlantAdapter choosePlantAdapter;
    private ChoosePlantViewModel choosePlantViewModel;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_choose_my_plant);

        back();
//        setContentView(R.layout.activity_add_my_plant);
        choosePlantViewModel = new ViewModelProvider(this).get(ChoosePlantViewModel.class);
        binding.setChoosePlantViewModel(choosePlantViewModel);
        binding.setLifecycleOwner(this);

        initBundleSearch(); //

        choosePlantViewModel.setChoosePlantNavigator(this);

        rcvAddMyPlant = findViewById(R.id.rcv_add_my_plant);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvAddMyPlant.setLayoutManager(linearLayoutManager);

//        addPlantAdapter = new AddPlantAdapter((List<Plant>) addPlantViewModel.getListPlant());
        choosePlantAdapter = new ChoosePlantAdapter(getListPlant());
        choosePlantAdapter.setChoosePlantNavigator(this);
        rcvAddMyPlant.setAdapter(choosePlantAdapter);
    }

    private void back() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
        toolbar.getNavigationIcon().setTint(getResources().getColor(R.color.colorGreenText));
        toolbar.setTitle(null);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @NonNull
    private List<Plant> getListPlant() {
        List<Plant> plants = new ArrayList<>();
        plants.add(new Plant(1, "Hoa hướng dương","", "https://cdn.tgdd.vn/Files/2021/08/03/1372812/dac-diem-nguon-goc-va-y-nghia-dac-biet-cua-hoa-huong-duong-202206031122479117.jpeg"));
        plants.add(new Plant(2, "Cây chuối","", "https://i.ex-cdn.com/mientay.giadinhonline.vn/files/content/2023/06/28/cu-cai-da-u-muoi-1057.jpeg"));
        plants.add(new Plant(3, "Hoa hướng dương","",  "https://cdn.tgdd.vn/Files/2021/08/03/1372812/dac-diem-nguon-goc-va-y-nghia-dac-biet-cua-hoa-huong-duong-202206031122479117.jpeg"));
        plants.add(new Plant(4, "Hoa hướng dương","",  "https://cdn.tgdd.vn/Files/2021/08/03/1372812/dac-diem-nguon-goc-va-y-nghia-dac-biet-cua-hoa-huong-duong-202206031122479117.jpeg"));
        plants.add(new Plant(5, "Hoa hướng dương","", "https://cdn.tgdd.vn/Files/2021/08/03/1372812/dac-diem-nguon-goc-va-y-nghia-dac-biet-cua-hoa-huong-duong-202206031122479117.jpeg"));
        plants.add(new Plant(6, "Cây ngô","", "https://tuyensinhso.vn/images/files/tuyensinhso.com/nganh-khoa-hoc-cay-trong.jpg"));
        return plants;
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
    public void handleAddPlant(Plant plant) {
        Intent intent;
        if (choosePlantViewModel.getIsSearch().getValue() == true) {
            intent = new Intent(this, PlantDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("plant", plant);
            intent.putExtras(bundle);
        } else {
            intent = new Intent(this, AddMyPlantActivity.class);
        }
        startActivity(intent);
    }
}
