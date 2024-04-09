package vn.edu.ptit.planta.ui.home.today;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.databinding.FragmentTodayBinding;
import vn.edu.ptit.planta.model.care.CareScheduleResponse;
import vn.edu.ptit.planta.model.myschedule.MySchedule;
import vn.edu.ptit.planta.ui.schedule.adapter.CareScheduleCategoryAdapter;
import vn.edu.ptit.planta.utils.DateUtils;
import vn.edu.ptit.planta.utils.TimeUtils;


public class TodayFragment extends Fragment {

    private FragmentTodayBinding binding;
    private TodayViewModel viewModel;
    private RecyclerView recyclerView;
    private CareScheduleCategoryAdapter adapter;

    private PendingIntent pendingIntent;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_today, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(TodayViewModel.class);
        binding.setTodayViewModel(viewModel);
        //binding.setLifecycleOwner(this);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        recyclerView = binding.rcvToday;

        adapter = new CareScheduleCategoryAdapter(requireContext());
        adapter.setActivityResultLauncher(mActivityResultLauncher); //
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        setAdapterSchedules();

        //setNotification();

        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(adapter != null) adapter.release();
    }

    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == Activity.RESULT_OK){
                viewModel.initData();
                setAdapterSchedules();
            }
        }
    });

    private void setAdapterSchedules() {
        viewModel.getListCareSchedules().observe(requireActivity(), new Observer<List<CareScheduleResponse>>() {
            @Override
            public void onChanged(List<CareScheduleResponse> careScheduleResponses) {
                adapter.setListCareScheduleCategorys(careScheduleResponses);
                adapter.setSelectDate(dateToday());
                recyclerView.setAdapter(adapter);
            }
        });
    }
    @NonNull
    private String dateToday() {
        Calendar calendar = Calendar.getInstance();
        String selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d",
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        return selectedDate;
    }


    private void setNotification() {

        List<MySchedule> mySchedules = new ArrayList<>();

        mySchedules.add(new MySchedule(1,"Bón phân", DateUtils.stringToDate("2024-04-05"), DateUtils.stringToDate("2024-06-01"), TimeUtils.stringToTime("17:42:00"), 1));
        mySchedules.add(new MySchedule(2,"Tưới nước", DateUtils.stringToDate("2024-04-01"), DateUtils.stringToDate("2024-06-01"), TimeUtils.stringToTime("17:45:00"), 1));
        mySchedules.add(new MySchedule(3,"Tưới nước", DateUtils.stringToDate("2024-04-01"), DateUtils.stringToDate("2024-06-01"), TimeUtils.stringToTime("08:30:00"), 1));
        mySchedules.add(new MySchedule(4,"Bón phân", DateUtils.stringToDate("2024-04-01"), DateUtils.stringToDate("2024-06-01"), TimeUtils.stringToTime("09:00:00"), 1));
        mySchedules.add(new MySchedule(5,"Bón phân", DateUtils.stringToDate("2024-04-01"), DateUtils.stringToDate("2024-06-01"), TimeUtils.stringToTime("17:44:00"), 1));

        for(int i=0; i< mySchedules.size(); i++) {
            scheduleNotification(mySchedules.get(i), i+1);
        }
    }
    private void scheduleNotification(@NonNull MySchedule schedule, int requestCode) {
        // Tạo intent để gửi đến ScheduleNotificationService
        Intent intent = new Intent(requireContext(), ScheduleNotificationReceiver.class);
        intent.putExtra("scheduleName", schedule.getName());
        intent.putExtra("scheduleId", requestCode);

        pendingIntent = PendingIntent.getBroadcast(requireContext(), requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Lập lịch thông báo
        AlarmManager alarmManager = (AlarmManager) requireContext().getSystemService(Context.ALARM_SERVICE);
        long timeInMillis = getTimeInMillis(DateUtils.formatToDDMMYYYY(schedule.getStartDate()), TimeUtils.formatToHHMM(schedule.getTime()));

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, getInterval(schedule.getFrequency()), pendingIntent);
    }


    private long getTimeInMillis(String date, String time) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            Date dateTime = dateFormat.parse(date + " " + time);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateTime);

            Log.e("test 222 3", calendar.getTime().toString());

            return calendar.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }
    private long getInterval(int frequency) {
        // Chuyển đổi frequency từ ngày sang milliseconds
        long oneDayInMillis = 24 * 60 * 60 * 1000; // 1 ngày trong milliseconds
        return frequency * oneDayInMillis;
    }
}