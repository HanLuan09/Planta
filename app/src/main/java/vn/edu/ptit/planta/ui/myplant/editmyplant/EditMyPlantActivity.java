package vn.edu.ptit.planta.ui.myplant.editmyplant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.AttrRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.model.AttributeOfMyPlant;
import vn.edu.ptit.planta.model.MyPlant;
import vn.edu.ptit.planta.ui.MainActivity;

public class EditMyPlantActivity extends AppCompatActivity {
    private EditMyPlantAdapter editMyPlantAdapter;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private ImageView ivChooseImage, ivImageMyplanta;
    private Button btnCancel, btnSave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_information_plant);

        back();
        editMyPlant();

        recyclerView = findViewById(R.id.recyclerview_edit_my_plant);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        MyPlant myPlant = getMyPlant();
        List<AttributeOfMyPlant> attributeOfMyPlants = getListAttributeOfMyPlant(myPlant);
        editMyPlantAdapter = new EditMyPlantAdapter(attributeOfMyPlants);
        recyclerView.setAdapter(editMyPlantAdapter);

        ivChooseImage = findViewById(R.id.iv_choose_image);
        ivImageMyplanta = findViewById(R.id.iv_image_myplanta);
        
        Glide.with(this)
                .load(myPlant.getImage())
                .placeholder(R.drawable.icon_no_image)
                .into(ivImageMyplanta);
    }

    private MyPlant getMyPlant() {
        return new MyPlant(1, "Hoa hướng dương", "https://cdn.tgdd.vn/Files/2021/08/03/1372812/dac-diem-nguon-goc-va-y-nghia-dac-biet-cua-hoa-huong-duong-202206031122479117.jpeg","Sáng", "2024/03/21");
    }

    private List<AttributeOfMyPlant> getListAttributeOfMyPlant(MyPlant myPlant) {
        List<AttributeOfMyPlant> attributeOfMyPlants = new ArrayList<>();
        attributeOfMyPlants.add(new AttributeOfMyPlant("Tên thực vật",myPlant.getName()));
        attributeOfMyPlants.add(new AttributeOfMyPlant("Ngày trồng",myPlant.getGrownDate()));
        attributeOfMyPlants.add(new AttributeOfMyPlant("Loại ánh sáng",myPlant.getKindOfLight()));
//        attributeOfMyPlants.add(new AttributeOfMyPlant("Kích thước trưởng thành",myPlant.getName()));
//        attributeOfMyPlants.add(new AttributeOfMyPlant("Thời gian trưởng thành",myPlant.getName()));
//        attributeOfMyPlants.add(new AttributeOfMyPlant("Mô tả",myPlant.getName()));

        return attributeOfMyPlants;
    }

    private void editMyPlant() {
        btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditMyPlantActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void back(){
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
}
