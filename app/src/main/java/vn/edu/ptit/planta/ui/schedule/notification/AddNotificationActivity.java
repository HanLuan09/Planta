package vn.edu.ptit.planta.ui.schedule.notification;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.databinding.ActivityAddNotificationBinding;
import vn.edu.ptit.planta.model.ScheduleMyPlant;
import vn.edu.ptit.planta.model.ScheduleNodtification;
import vn.edu.ptit.planta.ui.schedule.ScheduleViewModel;

public class AddNotificationActivity extends AppCompatActivity implements NotificationNavigator {

    private ActivityAddNotificationBinding binding;
    private AddNotificationViewModel viewModel;
    private ScheduleViewModel scheduleViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_notification);

        viewModel = new ViewModelProvider(this).get(AddNotificationViewModel.class);
        binding.setNotificationViewModel(viewModel);
        binding.setLifecycleOwner(this);


        scheduleViewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);

        viewModel.setNotificationNavigator(this);


        initBundle();
        initClick();
        binding.layoutStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showStartDatePickerDialog();
            }
        });

    }


    private void initBundle() {
        Bundle bundle = getIntent().getExtras();
        if(bundle == null) return;
        ScheduleMyPlant schedule = (ScheduleMyPlant) bundle.get("schedule_care");


    }

    private void initClick() {
        binding.layoutEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEndDatePickerDialog();
            }
        });

        binding.layoutTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog();
            }
        });

        binding.layoutFrequency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.getCheckDialog().setValue(1);
                FrequencyBottomSheet frequencyBottomSheet = new FrequencyBottomSheet();
                frequencyBottomSheet.show(getSupportFragmentManager(), frequencyBottomSheet.getTag());
            }
        });
    }

    private void showTimePickerDialog() {
        // Lấy thời gian hiện tại
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                        viewModel.getTime().setValue(selectedTime);
                    }
                },
                hour, minute, true);

        timePickerDialog.show();
    }

    private void showStartDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        // Tạo và hiển thị DatePickerDialog AlertDialog.THEME_HOLO_LIGHT
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);
                viewModel.getStartDate().setValue(selectedDate);
            }
        }, year, month, dayOfMonth);

        datePickerDialog.show();
    }

    private void showEndDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);
                viewModel.getEndDate().setValue(selectedDate);
            }
        }, year, month, dayOfMonth);

        datePickerDialog.show();
    }

    @Override
    public void handleCloseNotification() {
        finish();
    }

    @Override
    public void handleSummitNotification() {
        ScheduleNodtification scheduleNodtification = new ScheduleNodtification(
                viewModel.getExercise().getValue(),
                viewModel.getStartDate().getValue(),
                viewModel.getEndDate().getValue(),
                viewModel.getTime().getValue(),
                viewModel.getFrequency().getValue()
        );

        List<ScheduleNodtification> list = new ArrayList<>();
        if(scheduleViewModel.getListSchedules().getValue() != null)
            list = scheduleViewModel.getListSchedules().getValue();
        list.add(scheduleNodtification);

        scheduleViewModel.getListSchedules().setValue(list);
        finish();

        Log.e("Test" , scheduleViewModel.getListSchedules().getValue().size()+"");
    }













    private void showCustomDatePickerDialog() {
        // Lấy thời gian hiện tại
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Tạo DatePicker
        DatePicker datePicker = new DatePicker(this);
        datePicker.init(year, month, dayOfMonth, null);

        // Tạo AlertDialog.Builder với THEME_HOLO_DARK
        AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
        builder.setTitle("Select Date");
        builder.setView(datePicker);

        // Thiết lập nút OK
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Xử lý khi người dùng chọn OK
                int selectedYear = datePicker.getYear();
                int selectedMonth = datePicker.getMonth();
                int selectedDay = datePicker.getDayOfMonth();

                String selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);

                // Cập nhật giá trị ngày trong ViewModel hoặc thực hiện các hành động khác
                viewModel.getStartDate().setValue(selectedDate);
            }
        });

        // Thiết lập nút Cancel
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Xử lý khi người dùng chọn Cancel
                dialog.dismiss();
            }
        });

        // Hiển thị AlertDialog
        builder.show();
    }
}