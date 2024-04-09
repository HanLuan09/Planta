package vn.edu.ptit.planta.ui.test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import vn.edu.ptit.planta.databinding.ActivityTestBinding;
import vn.edu.ptit.planta.ui.home.today.ScheduleNotificationReceiver;

import android.view.View;

import java.util.Calendar;

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
}