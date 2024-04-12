package vn.edu.ptit.planta.ui.myplant.editmyplant;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.data.RetrofitClient;
import vn.edu.ptit.planta.model.ApiResponse;
import vn.edu.ptit.planta.model.AttributeOfMyPlant;
import vn.edu.ptit.planta.model.myplant.MyPlant;
import vn.edu.ptit.planta.ui.MainActivity;

public class EditMyPlantActivity extends AppCompatActivity implements EditMyPlantNavigator{
    private Toolbar toolbar;
    private EditText etName, etGrownDate, etKindOfLight;
    private ImageView ivChooseDate, ivChooseImage, ivImageMyplanta;
    private Button btnDelete, btnSave;
    private ActivityResultLauncher resultLauncher;
    private MyPlant myPlant, myPlantRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_information_plant);

        initData();
        registerResult();

        ivChooseDate = findViewById(R.id.iv_choose_date);
        ivChooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseDate(myPlant.getGrownDate());
            }
        });
        etGrownDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseDate(myPlant.getGrownDate());
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
                    updateMyPlant();
                }
            }
        });

        btnDelete = findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteMyPlant();
            }
        });
        back();
    }
    private void initData(){
        getMyPlant();

        etName = findViewById(R.id.et_content_name_myplanta);
        etGrownDate = findViewById(R.id.et_content_growndate_myplanta);
        etKindOfLight = findViewById(R.id.et_content_kindoflight_myplanta);
        ivImageMyplanta = findViewById(R.id.iv_image_myplanta);

        etName.setText(myPlant.getName());
        etGrownDate.setText(myPlant.getGrownDate());
        etKindOfLight.setText(myPlant.getKindOfLight());
        glideImage(myPlant.getImage(), ivImageMyplanta);
    };

    private void chooseDate(String oldDate){
        String[] s = oldDate.split("-");
        Calendar calendar = Calendar.getInstance();
        int date = Integer.valueOf(s[2]);
        int month = Integer.valueOf(s[1]);
        int year = Integer.valueOf(s[0]);
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
                            Glide.with(EditMyPlantActivity.this)
                                    .load(imageUri)
                                    .placeholder(R.drawable.icon_no_image)
                                    .into(ivImageMyplanta);
                        }catch (Exception e){
                            Toast.makeText(EditMyPlantActivity.this, "No image selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    private void chooseImage(){
        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        resultLauncher.launch(intent);
    }

    private void getMyPlant() {
        Bundle bundle = getIntent().getExtras();
        myPlant = null;
        if(bundle.containsKey("myplant")){
            myPlant = (MyPlant) bundle.getSerializable("myplant");
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
//        myPlantRequest.setImage(convertImageToBase64(ivImageMyplanta));
        myPlantRequest.setImage(ivImageMyplanta.toString());
        return true;
    }

    private String convertImageToBase64(ImageView imageView){
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

    private void updateMyPlant() {
        RetrofitClient.getMyPlantService().updateMyPlant(myPlant.getId(), myPlantRequest).enqueue(new Callback<ApiResponse<Boolean>>() {
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

    private void deleteMyPlant() {
        RetrofitClient.getMyPlantService().deleteMyPlant(myPlant.getId()).enqueue(new Callback<ApiResponse<Boolean>>() {
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
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(EditMyPlantActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void responseFalse(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void responseInvalid(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void connectFail(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void glideImage(String image, ImageView imageView) {
        if (image != null) {
            byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

            Glide.with(EditMyPlantActivity.this)
                    .load(bitmap)
                    .placeholder(R.drawable.icon_no_mob)
                    .into(imageView);
        } else {
            imageView.setImageResource(R.drawable.icon_no_image);
        }
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
