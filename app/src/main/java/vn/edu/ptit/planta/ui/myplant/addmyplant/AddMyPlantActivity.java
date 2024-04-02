package vn.edu.ptit.planta.ui.myplant.addmyplant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.model.AttributeOfMyPlant;
import vn.edu.ptit.planta.model.myplant.MyPlant;
import vn.edu.ptit.planta.ui.schedule.ScheduleActivity;

public class AddMyPlantActivity extends AppCompatActivity {
    private AddMyPlantAdapter addMyPlantAdapter;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private ImageView ivChooseImage, ivImageMyplanta;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_add_information_plant);

        back();
        addMyPlant();

        recyclerView = findViewById(R.id.recyclerview_add_my_plant);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        List<AttributeOfMyPlant> attributeOfMyPlants = getListAttributeOfMyPlant();
        addMyPlantAdapter = new AddMyPlantAdapter(attributeOfMyPlants);
        recyclerView.setAdapter(addMyPlantAdapter);

        ivChooseImage = findViewById(R.id.iv_choose_image);
        ivImageMyplanta = findViewById(R.id.iv_image_myplanta);

        Glide.with(this)
                .load(R.drawable.icon_no_image)
                .into(ivImageMyplanta);
    }

    private MyPlant getMyPlant() {
        return new MyPlant(1, "Hoa hướng dương", "https://cdn.tgdd.vn/Files/2021/08/03/1372812/dac-diem-nguon-goc-va-y-nghia-dac-biet-cua-hoa-huong-duong-202206031122479117.jpeg","Sáng", "2024/03/21");
    }

    private List<AttributeOfMyPlant> getListAttributeOfMyPlant() {
        List<AttributeOfMyPlant> attributeOfMyPlants = new ArrayList<>();
        attributeOfMyPlants.add(new AttributeOfMyPlant("Tên thực vật","Nhập tên thực vật"));
        attributeOfMyPlants.add(new AttributeOfMyPlant("Ngày trồng","Nhập ngày trồng"));
        attributeOfMyPlants.add(new AttributeOfMyPlant("Loại ánh sáng","Nhập loại ánh sáng"));
//        attributeOfMyPlants.add(new AttributeOfMyPlant("Kích thước trưởng thành",myPlant.getName()));
//        attributeOfMyPlants.add(new AttributeOfMyPlant("Thời gian trưởng thành",myPlant.getName()));
//        attributeOfMyPlants.add(new AttributeOfMyPlant("Mô tả",myPlant.getName()));

        return attributeOfMyPlants;
    }

    public void addMyPlant(){
        btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddMyPlantActivity.this, ScheduleActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void back(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
