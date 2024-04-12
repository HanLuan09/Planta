package vn.edu.ptit.planta.ui.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.data.RetrofitClient;
import vn.edu.ptit.planta.databinding.ActivityTestBinding;
import vn.edu.ptit.planta.utils.ImageUtils;

import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;

public class TestActivity extends AppCompatActivity {

    private ActivityTestBinding binding;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.text.setText("Dừng đặt giờ");
                if(pendingIntent != null) pendingIntent.cancel();
            }
        });

        Uri imageUri = getDrawableUri(this, R.drawable.plant_img);
//        Uri imageUri = Uri.parse("content://media/external/images/media/1234");
        String mimeType = ImageUtils.getMimeType(this, imageUri);
        String base64Image = ImageUtils.imageToBase64(this, imageUri);

        if (!base64Image.isEmpty()) {
            Log.e("Base64 Image", ImageUtils.formattedBase64(mimeType, base64Image));
        }
        Glide.with(this)
                .load(ImageUtils.formattedBase64(mimeType, base64Image))
                .placeholder(R.drawable.icon_no_mob)
                .override(300,300)
                .into(binding.testIv);


//        binding.btn2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTimeInMillis(System.currentTimeMillis());
//                calendar.set(Calendar.HOUR_OF_DAY, binding.time.getCurrentHour());
//                calendar.set(Calendar.MINUTE, binding.time.getCurrentMinute());
//
//                binding.text.setText(calendar.getTime().toString());
//
//                Intent intent = new Intent(TestActivity.this, ScheduleNotificationReceiver.class);
//
//                intent.setAction("MyAction");
//                intent.putExtra("scheduleName", "test");
//
//                PendingIntent pendingIntent = PendingIntent.getBroadcast(TestActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//                // Lập lịch thông báo
//                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                        11111000 , pendingIntent);
//            }
//        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if ((pendingIntent != null)) pendingIntent.cancel();
    }

    public static Uri getDrawableUri(@NonNull Context context, int drawableId) {
        return Uri.parse("android.resource://" + context.getPackageName() + "/" + drawableId);
    }
}