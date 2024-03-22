package vn.edu.ptit.planta.ui.test;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.work.Data;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ScheduleViewModel extends ViewModel {

    private MutableLiveData<List<Schedule>> schedules = new MutableLiveData<>();
    private Context mContext;

    public ScheduleViewModel() {
        getSchedulesFromServer();
    }

    public LiveData<List<Schedule>> getSchedules() {
        return schedules;
    }

    private void getSchedulesFromServer() {
        // Thay thế bằng API lấy dữ liệu từ server
        List<Schedule> schedules = new ArrayList<>();
        schedules.add(new Schedule("Tưới cây", "8:30", 2));
        schedules.add(new Schedule("Đi họp", "17:33", 1));
        schedules.add(new Schedule("Đi chơi", "17:38", 1));
        schedules.add(new Schedule("Bón phân", "17:40", 1));
        schedules.add(new Schedule("Đi họp", "17:41", 1));

        this.schedules.postValue(schedules);
    }

    public void scheduleNotification(Schedule schedule) {
        // Sử dụng WorkManager để tạo công việc chạy ngầm để hiển thị thông báo
        WorkManager.getInstance(mContext).enqueue(
                new PeriodicWorkRequest.Builder(ScheduleWorker.class, schedule.getFrequency(), TimeUnit.DAYS)
                        .setInputData(
                                new Data.Builder()
                                        .putString("name", schedule.getName())
                                        .putString("time", schedule.getTime())
                                        .build()
                        )
                        .build()
        );
    }
}
