package vn.edu.ptit.planta.ui.home.today;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.databinding.FragmentTodayBinding;
import vn.edu.ptit.planta.model.care.CareScheduleResponse;
import vn.edu.ptit.planta.model.myplant.MyPlantScheduleResponse;
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

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        int idUser = sharedPreferences.getInt("idUser",0);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_today, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(TodayViewModel.class);
        binding.setTodayViewModel(viewModel);
        //binding.setLifecycleOwner(this);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        Glide.with(this)
                .load(R.drawable.loading)
                .into(binding.loading);

        if(idUser != 0) {
            viewModel.getUserId().setValue(idUser);
            viewModel.initDataMyPlantSchedule();

            recyclerView = binding.rcvToday;

            adapter = new CareScheduleCategoryAdapter(requireContext());
            adapter.setActivityResultLauncher(mActivityResultLauncher); //
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
            recyclerView.setLayoutManager(linearLayoutManager);

            observeAdapterSchedules();

            observeMyPlantSchedule();
        }

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
                viewModel.initDataMyPlantSchedule();
                observeAdapterSchedules();
            }
        }
    });

    private void observeAdapterSchedules() {
        viewModel.getListCareSchedules().observe(requireActivity(), new Observer<List<CareScheduleResponse>>() {
            @Override
            public void onChanged(List<CareScheduleResponse> careScheduleResponses) {
                adapter.setListCareScheduleCategorys(careScheduleResponses);
                adapter.setSelectDate(dateToday());
                recyclerView.setAdapter(adapter);
            }
        });
    }

    private void observeMyPlantSchedule() {
        viewModel.getListMyPlantSchedules().observe(requireActivity(), new Observer<List<MyPlantScheduleResponse>>() {
            @Override
            public void onChanged(List<MyPlantScheduleResponse> myPlantScheduleResponses) {

                for(MyPlantScheduleResponse myPlant : myPlantScheduleResponses){

                    for(MySchedule mySchedule : myPlant.getMySchedules()){
                        scheduleNotification(mySchedule, myPlant.getName());
                    }

                }
            }
        });
    }


    private void scheduleNotification(@NonNull MySchedule schedule, String name) {

        String uniqueId = name + "_" + schedule.getId();
        int requestCode = uniqueId.hashCode();

        Intent intent = new Intent(requireContext(), ScheduleNotificationReceiver.class);
        intent.putExtra("myPlantName", name);
        intent.putExtra("scheduleName", schedule.getName());
        intent.putExtra("scheduleId", requestCode);

        pendingIntent = PendingIntent.getBroadcast(requireContext(), requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Lập lịch thông báo
        AlarmManager alarmManager = (AlarmManager) requireContext().getSystemService(Context.ALARM_SERVICE);

        Date currentDate = DateUtils.stringToDate(dateToday());
        if(!currentDate.after(schedule.getEndDate())){
            long timeInMillis = getTimeInMillis(DateUtils.formatToDDMMYYYY(schedule.getStartDate()), TimeUtils.formatToHHMM(schedule.getTime()), schedule.getFrequency());
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, getInterval(schedule.getFrequency()), pendingIntent);
        }else {
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
        }
    }


    private long getTimeInMillis(String date, String time, int frequency) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            java.util.Date dateTime = dateFormat.parse(date + " " + time);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateTime);

            Calendar localCalendar = Calendar.getInstance();
            localCalendar.setTimeInMillis(System.currentTimeMillis());

            if(calendar.getTimeInMillis() >= localCalendar.getTimeInMillis()) {
                return calendar.getTimeInMillis();
            }
            String[] s = time.split(":");
            calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(s[0]));
            calendar.set(Calendar.MINUTE, Integer.valueOf(s[1]));

            //Log.e("Test date", DateUtils.daysBetweenTodayAndStartDate(date)%frequency+ " "+ frequency);
            if(calendar.getTimeInMillis() >= localCalendar.getTimeInMillis()) {
                return calendar.getTimeInMillis();
            }

            int updateFrequency = (int) DateUtils.daysBetweenTodayAndStartDate(date) % frequency;
            return calendar.getTimeInMillis() + getInterval(updateFrequency + 1);

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

    @NonNull
    private String dateToday() {
        Calendar calendar = Calendar.getInstance();
        String selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d",
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        return selectedDate;
    }
}