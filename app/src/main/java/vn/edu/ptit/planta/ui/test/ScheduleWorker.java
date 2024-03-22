package vn.edu.ptit.planta.ui.test;

import android.app.NotificationManager;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.Calendar;
import java.util.Date;

import vn.edu.ptit.planta.R;

public class ScheduleWorker extends Worker {

    private static final String CHANNEL_ID = "201"; // Đặt ID của kênh thông báo ở đây

    public ScheduleWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    public Result doWork() {
        // Lấy dữ liệu lịch trình mới từ server
        // Hiển thị thông báo nếu có thay đổi

        String name = getInputData().getString("name");
        String time = getInputData().getString("time");

        String[] timeParts = time.split(":");
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);

        // Lấy thời gian hiện tại
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        // Kiểm tra nếu thời gian đã qua, thì cập nhật cho ngày tiếp theo
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }


        // Hiển thị thông báo
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setContentTitle(name)
                .setContentText("Lịch trình của bạn sắp bắt đầu")
                .setSmallIcon(R.drawable.ic_notification)
                .setAutoCancel(true)
                .setWhen(calendar.getTimeInMillis());

        NotificationManager notificationManager =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(getNotificationId(), builder.build());

        return Result.success();
    }
    private int getNotificationId() {
        return (int)new Date().getTime();
    }
}
