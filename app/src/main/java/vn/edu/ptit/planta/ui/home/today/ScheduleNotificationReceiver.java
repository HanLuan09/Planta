package vn.edu.ptit.planta.ui.home.today;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.ui.MainActivity;

public class ScheduleNotificationReceiver extends BroadcastReceiver {

    private final String CHANNEL_ID = "LH1001";

    @Override
    public void onReceive(@NonNull Context context, @NonNull Intent intent) {
        // Lấy dữ liệu lịch trình từ intent
        String myPlantName = intent.getStringExtra("myPlantName");
        String scheduleName = intent.getStringExtra("scheduleName");
        int scheduleId = intent.getIntExtra("scheduleId", 0);

        //Log.e("Test Noti", scheduleId+ "");

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Notification Schedule My Plant", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Schedule My Plant");
            notificationManager.createNotificationChannel(channel);
        }

        // Tạo Intent để mở MainActivity khi nhấn vào thông báo
        Intent resultIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, scheduleId, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Tạo thông báo
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.icon_alarm_2)
                .setContentTitle(myPlantName + ": " + scheduleName)
                .setContentText("Đến giờ chăm sóc cây của bạn rồi")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Đến giờ chăm sóc cây của bạn rồi"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // Hiển thị thông báo
        notificationManager.notify(scheduleId, builder.build());
    }
}
