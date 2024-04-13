package vn.edu.ptit.planta.ui.myplant.editmyplant;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
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

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.data.RetrofitClient;
import vn.edu.ptit.planta.model.ApiResponse;
import vn.edu.ptit.planta.model.myplant.MyPlant;
import vn.edu.ptit.planta.model.myplant.MyPlantRequest;
import vn.edu.ptit.planta.ui.MainActivity;
import vn.edu.ptit.planta.utils.ImageUtils;

public class EditMyPlantActivity extends AppCompatActivity implements EditMyPlantNavigator{
    private Toolbar toolbar;
    private EditText etName, etGrownDate, etKindOfLight;
    private ImageView ivChooseDate, ivChooseImage, ivImageMyplanta;
    private Button btnDelete, btnSave;
    private ActivityResultLauncher resultLauncher;
    private MyPlant myPlant;
    private MyPlantRequest myPlantRequest;
    private Uri imageUri;

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
                            imageUri = result.getData().getData();
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
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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

        myPlantRequest = new MyPlantRequest();
        myPlantRequest.setName(name);
        myPlantRequest.setGrownDate(grownDate);
        myPlantRequest.setKindOfLight(kindOfLight);

        String mimeType = ImageUtils.getMimeType(this, imageUri);
        String base64Image = ImageUtils.imageToBase64(this, imageUri);
        myPlantRequest.setImage(ImageUtils.formattedBase64(mimeType, base64Image));

        return true;
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
        Glide.with(EditMyPlantActivity.this)
                .load(image)
                .placeholder(R.drawable.icon_no_image)
                .into(imageView);
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
