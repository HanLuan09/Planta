package vn.edu.ptit.planta.ui.myplant.addmyplant;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.data.RetrofitClient;
import vn.edu.ptit.planta.model.ApiResponse;
import vn.edu.ptit.planta.model.AttributeOfMyPlant;
import vn.edu.ptit.planta.model.PlantResponseOfMyPlant;
import vn.edu.ptit.planta.model.myplant.MyPlant;
import vn.edu.ptit.planta.model.plant.Plant;
import vn.edu.ptit.planta.ui.MainActivity;
import vn.edu.ptit.planta.ui.myplant.editmyplant.EditMyPlantActivity;
import vn.edu.ptit.planta.ui.schedule.ScheduleActivity;

public class AddMyPlantActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText etName, etGrownDate, etKindOfLight;
    private ImageView ivChooseDate, ivChooseImage, ivImageMyplanta;
    private Button btnSave;
    private ProgressBar progressBar;
    private Bundle bundle;
    private ActivityResultLauncher resultLauncher;
    private int idUser;
    private Plant plant;
    private MyPlant myPlantRequest;
    private PlantResponseOfMyPlant plantResponseOfMyPlant;


    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_add_information_plant);

        initData();
        registerResult();

        ivChooseDate = findViewById(R.id.iv_choose_date);
        ivChooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseDate();
            }
        });
        etGrownDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseDate();
            }
        });

        ivChooseImage = findViewById(R.id.iv_choose_image);
        ivChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });
        ivImageMyplanta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkMyPlantRequest()){
                    btnSave.setEnabled(false);

                    progressBar = findViewById(R.id.progressBar);
                    progressBar.setVisibility(View.VISIBLE);
                    addMyPlant();
                    btnSave.setEnabled(true);
                }
            }
        });

        back();
    }

    public void initData(){
        etName = findViewById(R.id.et_content_name_myplanta);
        etGrownDate = findViewById(R.id.et_content_growndate_myplanta);
        etKindOfLight = findViewById(R.id.et_content_kindoflight_myplanta);
        ivImageMyplanta = findViewById(R.id.iv_image_myplanta);

        SharedPreferences sharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE);
        idUser = sharedPreferences.getInt("idUser", 0);

        getPlant();
        Log.e("NAME PLANT", plant.getName() + ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        plantResponseOfMyPlant = new PlantResponseOfMyPlant();
        plantResponseOfMyPlant.setId(plant.getId());
    }

    private void chooseDate(){
        Calendar calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                calendar.set(year, month, date);
                etGrownDate.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, year, month, date);
        datePickerDialog.show();
    }

    private  void registerResult(){
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        try {
                            Uri imageUri = result.getData().getData();
                            Glide.with(AddMyPlantActivity.this)
                                    .load(imageUri)
                                    .placeholder(R.drawable.icon_no_image)
                                    .into(ivImageMyplanta);
                        }catch (Exception e){
                            Toast.makeText(AddMyPlantActivity.this, "No image selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    private void chooseImage(){
        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        resultLauncher.launch(intent);
    }

    private void getPlant() {
        Bundle bundle = getIntent().getExtras();
        plant = null;
        if(bundle.containsKey("plant")){
            plant = (Plant) bundle.getSerializable("plant");
        }
    }

    private boolean checkMyPlantRequest() {
        String name = etName.getText().toString();
        String grownDate = etGrownDate.getText().toString();
        String kindOfLight = etKindOfLight.getText().toString();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Vui lòng nhập tên cây trồng!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(TextUtils.isEmpty(grownDate)){
            Toast.makeText(this, "Vui lòng chọn ngày trồng cây!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(TextUtils.isEmpty(kindOfLight)){
            Toast.makeText(this, "Vui lòng nhập loại ánh sáng!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(ivImageMyplanta.getDrawable() == null){
            Toast.makeText(this, "Vui lòng chọn ảnh cây trồng!", Toast.LENGTH_SHORT).show();
            return false;
        }

        myPlantRequest = new MyPlant();
        myPlantRequest.setName(name);
        myPlantRequest.setGrownDate(grownDate);
        myPlantRequest.setKindOfLight(kindOfLight);
        myPlantRequest.setImage(convertImageToString(ivImageMyplanta));
        myPlantRequest.setPlantDetailOfMyPlant(plantResponseOfMyPlant);
        return true;
    }

    private String convertImageToString(ImageView imageView){

        // Lấy bitmap từ ImageView
        Bitmap bitmap = ((BitmapDrawable) ivImageMyplanta.getDrawable()).getBitmap();

        // Tạo mảng lưu trữ bit map
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // Thay đổi định dạng nén theo định dạng của ảnh ban đầu
        Bitmap.CompressFormat compressFormat;
        if (bitmap.hasAlpha()) {
            compressFormat = Bitmap.CompressFormat.PNG; // Giữ nguyên định dạng PNG nếu có kênh alpha
        } else {
            compressFormat = Bitmap.CompressFormat.JPEG; // Nén về JPEG nếu không có kênh alpha
        }

        // Nén bitmap với định dạng và chất lượng tương ứng
        bitmap.compress(compressFormat, 100, byteArrayOutputStream);

        // Chuyển đổi ByteArrayOutputStream thành mảng byte
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        // Sử dụng mã hóa Base64 để chuyển đổi mảng byte thành chuỗi Base64
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private void addMyPlant() {
        RetrofitClient.getMyPlantService().addMyPlant(idUser, myPlantRequest).enqueue(new Callback<ApiResponse<Boolean>>() {
            @Override
            public void onResponse(Call<ApiResponse<Boolean>> call, Response<ApiResponse<Boolean>> response) {
                if(response.isSuccessful()){
                    ApiResponse<Boolean> apiResponse = response.body();
                    if(apiResponse.isSuccess()){
                        String message = apiResponse.getMessage();
                        responseTrue(message);
                    }
                    else{
                        String message = apiResponse.getMessage();
                        responseFalse(message);
                    }
                }
                else{
                    String message = "Invalid response";
                    responseInvalid(message);
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<Boolean>> call, Throwable throwable) {
                String message = "Fail connect to server";
                connectFail(message);
            }
        });
    }

    private void responseTrue(String message) {
        Toast.makeText(this, message + " Continue set schedule!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AddMyPlantActivity.this, ScheduleActivity.class);
        startActivity(intent);
    }

    private void responseFalse(String message) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void responseInvalid(String message) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void connectFail(String message) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
