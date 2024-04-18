package vn.edu.ptit.planta.ui.myplant.search;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.databinding.ActivitySearchMyPlantBinding;
import vn.edu.ptit.planta.model.myplant.MyPlant;
import vn.edu.ptit.planta.ui.myplant.editmyplant.EditMyPlantActivity;
import vn.edu.ptit.planta.ui.myplant.myplantdetail.MyPlantDetailActivity;


public class SearchMyPlantActivity extends AppCompatActivity implements SearchMyPlantNavigator{

    private ActivitySearchMyPlantBinding binding;
    private SearchMyPlantViewModel searchMyPlantViewModel;
    private SearchMyPlantAdapter searchMyPlantAdapter;
    private RecyclerView recyclerView;
    private ImageView ivSearch, ivBackSpace;
    private TextInputLayout tilSearch;
    private TextInputEditText tietSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding =  DataBindingUtil.setContentView(this, R.layout.activity_search_my_plant);

        ivSearch = findViewById(R.id.iv_search);
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tilSearch = findViewById(R.id.til_search_my_plant);
                tietSearch = (TextInputEditText) tilSearch.getEditText();
                if(tietSearch.getText().toString().isEmpty()){
                    Toast.makeText(SearchMyPlantActivity.this, "Vui lòng nhập từ khóa!", Toast.LENGTH_SHORT).show();
                } else {
                    initRecycleView();
                }
            }
        });

        ivBackSpace = findViewById(R.id.backspaceImageView);
        ivBackSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBack();
            }
        });
    }

    private void initRecycleView() {
        SharedPreferences sharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE);
        int idUser = sharedPreferences.getInt("idUser",0);

        searchMyPlantViewModel = new ViewModelProvider(this).get(SearchMyPlantViewModel.class);
        binding.setSearchMyPlantViewModel(searchMyPlantViewModel);
        binding.setLifecycleOwner(this);

        searchMyPlantViewModel.setSearchMyPlantNavigator(this);
        searchMyPlantViewModel.setIdUserResponse(idUser);
        searchMyPlantViewModel.setTextSearch(tietSearch.getText().toString());

        recyclerView = binding.idRcvSearchMyplant;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        // Quan sát dữ liệu thay đổi
        searchMyPlantViewModel.getListMyPlants().observe(this, new Observer<List<MyPlant>>() {
            @Override
            public void onChanged(List<MyPlant> myPlants) {
                if (searchMyPlantAdapter == null) {
                    // Nếu adapter chưa được tạo
                    searchMyPlantAdapter = new SearchMyPlantAdapter(myPlants);
                    searchMyPlantAdapter.setSearchMyPlantNavigator(SearchMyPlantActivity.this);
                    recyclerView.setAdapter(searchMyPlantAdapter);
                } else {
                    // Nếu adapter đã tồn tại, cập nhật dữ liệu mới
                    searchMyPlantAdapter.updateData(myPlants);
                }
            }
        });
    }
    @Override
    public void handleMyPlantDetail(MyPlant myPlant) {
        Intent intent = new Intent(this, MyPlantDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("myplant",myPlant);

        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    @Override
    public void handleBack() {
        finish();
    }

    @Override
    public void glideImage(String image, ShapeableImageView shapeableImageView) {
        Glide.with(this)
                .load(image)
                .placeholder(R.drawable.icon_no_image)
                .override(100, 100)
                .into(shapeableImageView);
    }
}