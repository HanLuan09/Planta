package vn.edu.ptit.planta.ui.home.today;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.ui.MainActivity;

public class ScheduleNotificationWorker extends Worker {

    private static final String CHANNEL_ID = "LH1002";

    public ScheduleNotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String scheduleName = getInputData().getString("scheduleName");
        int scheduleId = getInputData().getInt("scheduleId", 0);

        // Gửi thông báo
        sendNotification(scheduleName, scheduleId, getApplicationContext());

        return Result.success();
    }

    private void sendNotification(String scheduleName, int scheduleId, @NonNull Context context) {
        // Log ra để kiểm tra xem Worker có chạy hay không
        Log.e("Noti 2", "Msesss");

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
                .setContentTitle(scheduleName)
                .setContentText("Đến giờ "+ scheduleName+ " của bạn rồi")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Đến giờ "+ scheduleName+ " của bạn rồi"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // Hiển thị thông báo
        //notificationManager.notify(scheduleId, builder.build());
    }
}
