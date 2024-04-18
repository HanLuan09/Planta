package vn.edu.ptit.planta.ui.myplant.addmyplant;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.data.RetrofitClient;
import vn.edu.ptit.planta.model.ApiResponse;
import vn.edu.ptit.planta.model.myplant.MyPlantRequest;
import vn.edu.ptit.planta.model.plant.Plant;
import vn.edu.ptit.planta.ui.schedule.ScheduleActivity;
import vn.edu.ptit.planta.utils.ImageUtils;

public class AddMyPlantActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText etName, etGrownDate, etKindOfLight;
    private RadioButton rbWhite, rbRed, rbYellow, rbViolet;
    private ImageView ivChooseDate, ivChooseImage, ivImageMyplanta;
    private Button btnSave;
    private ProgressBar progressBar;
    private Bundle bundle;
    private ActivityResultLauncher resultLauncher;
    private int idUser;
    private Plant plant;
    private MyPlantRequest myPlantRequest;
    private Uri imageUri;


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
                new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        try {
                            imageUri = result.getData().getData();
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
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        resultLauncher.launch(intent);
    }

    private void getPlant() {
        bundle = getIntent().getExtras();
        plant = null;
        if(bundle.containsKey("plant")){
            plant = (Plant) bundle.getSerializable("plant");
        }
    }

    private boolean checkMyPlantRequest() {
        String name = etName.getText().toString();
        String grownDate = etGrownDate.getText().toString();
        String kindOfLight = etKindOfLight.getText().toString();

        if(TextUtils.isEmpty(name) && TextUtils.isEmpty(grownDate) && TextUtils.isEmpty(kindOfLight) && ivImageMyplanta.getDrawable() == null){
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return false;
        }

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

        myPlantRequest = new MyPlantRequest();
        myPlantRequest.setName(name);
        myPlantRequest.setGrownDate(grownDate);
        myPlantRequest.setKindOfLight(kindOfLight);

        String mimeType = ImageUtils.getMimeType(this, imageUri);
        String base64Image = ImageUtils.imageToBase64(this, imageUri);
        myPlantRequest.setImage(ImageUtils.formattedBase64(mimeType, base64Image));

        myPlantRequest.setIdPlant(plant.getId());
        return true;
    }

    // Hàm không dùng nữa
    public static Uri getDrawableUri(@NonNull Context context, int drawableId) {
        return Uri.parse("android.resource://" + context.getPackageName() + "/" + drawableId);
    }

    private void addMyPlant() {
        RetrofitClient.getMyPlantService().addMyPlant(idUser, myPlantRequest).enqueue(new Callback<ApiResponse<MyPlantRequest>>() {
            @Override
            public void onResponse(Call<ApiResponse<MyPlantRequest>> call, Response<ApiResponse<MyPlantRequest>> response) {
                if(response.isSuccessful()){
                    ApiResponse<MyPlantRequest> apiResponse = response.body();
                    if(apiResponse.isSuccess()){
                        MyPlantRequest myPlantResponse = apiResponse.getResult();
                        String message = apiResponse.getMessage();
                        responseTrue(message, myPlantResponse);
                    }
                    else{
                        String message = apiResponse.getMessage();
                        responseFalse(message);
                    }
                }
                else{
                    String message = "Phản hồi không hợp lệ!";
                    responseInvalid(message);
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<MyPlantRequest>> call, Throwable throwable) {
                String message = "Kết nối tới server thất bại!";
                connectFail(message);
            }
        });
    }

    private void responseTrue(String message, MyPlantRequest myPlantResponse) {
        Bundle bundle = new Bundle();
        bundle.putInt("my_plant_id",myPlantResponse.getId());

        Toast.makeText(this, message + " Tiếp tục đặt lịch trình!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AddMyPlantActivity.this, ScheduleActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
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
