package vn.edu.ptit.planta.ui.schedule.notification;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.databinding.ActivityAddNotificationBinding;
import vn.edu.ptit.planta.model.ScheduleMyPlant;
import vn.edu.ptit.planta.model.ScheduleNodtification;
import vn.edu.ptit.planta.model.myschedule.MySchedule;
import vn.edu.ptit.planta.model.myschedule.MyScheduleRequest;
import vn.edu.ptit.planta.ui.myplant.myplantdetail.care.CareFragment;
import vn.edu.ptit.planta.ui.schedule.ScheduleViewModel;
import vn.edu.ptit.planta.utils.DateUtils;
import vn.edu.ptit.planta.utils.TimeUtils;

public class AddNotificationActivity extends AppCompatActivity implements NotificationNavigator {

    private ActivityAddNotificationBinding binding;
    private AddNotificationViewModel viewModel;

    private int careId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_notification);

        viewModel = new ViewModelProvider(this).get(AddNotificationViewModel.class);
        binding.setNotificationViewModel(viewModel);
        binding.setLifecycleOwner(this);

        viewModel.setNotificationNavigator(this);

        initBundle();
        initObserveDate();

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(!viewModel.getIsCheckEdit().getValue()) {
                    final Dialog dialog = new Dialog(AddNotificationActivity.this);
                    openDialog(dialog, "Thông báo", "Lịch trình này sẽ không được lưu!", 2);
                    dialog.show();
                }else {
                    finish();
                }
            }
        });

    }

    private void initBundle() {
        Bundle bundle = getIntent().getExtras();
        if(bundle == null) {
            binding.ivNotiDelete.setVisibility(View.GONE);
            binding.tvNoti.setText("Add Notification");
            return;
        }
        MySchedule schedule = (MySchedule)bundle.get("schedule_care");

        careId = bundle.getInt("care_id");
        binding.ivNotiDelete.setVisibility(careId == 0? View.GONE : View.VISIBLE);
        binding.tvNoti.setText(careId == 0? "Add Notification" : "Notification");

        if(schedule != null) {
            viewModel.getIdMySchedule().setValue(schedule.getId());
            viewModel.getIsCheckEdit().setValue(true); //
            viewModel.getExercise().setValue(schedule.getName());
            viewModel.getStartDate().setValue(schedule.getStartDate().toString());
            viewModel.getEndDate().setValue(schedule.getEndDate().toString());
            viewModel.getTime().setValue(TimeUtils.formatToHHMM(schedule.getTime()));
            viewModel.getFrequency().setValue(schedule.getFrequency());
        }
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void handleStartDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);
                viewModel.getStartDate().setValue(selectedDate);
            }
        }, year, month, dayOfMonth);

        datePickerDialog.show();
    }

    @Override
    public void handleEndDatePickerDialog() {
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
    public void handleTimePickerDialog() {
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

    @Override
    public void handleFrequencyDialog() {
        viewModel.getCheckDialog().setValue(1);
        FrequencyBottomSheet frequencyBottomSheet = new FrequencyBottomSheet();
        frequencyBottomSheet.show(getSupportFragmentManager(), frequencyBottomSheet.getTag());
    }

    @Override
    public void handleExerciseDialog() {

    }

    @Override
    public void handleDeleteNotification() {
        final Dialog dialog = new Dialog(this);
        openDialog(dialog, "Cảnh báo", "Bạn có muốn xóa lịch trình này không?", 1);
        dialog.show();
    }

    @Override
    public void handleCloseNotification() {
        if(!viewModel.getIsCheckEdit().getValue()) {
            final Dialog dialog = new Dialog(this);
            openDialog(dialog, "Thông báo", "Lịch trình này sẽ không được lưu!", 2);
            dialog.show();
        }else {
            finish();
        }
    }

    @Override
    public void handleSummitNotification() {

        MyScheduleRequest myScheduleRequest = new MyScheduleRequest(
                viewModel.getExercise().getValue(),
                DateUtils.stringToDate(viewModel.getStartDate().getValue()),
                DateUtils.stringToDate(viewModel.getEndDate().getValue()),
                TimeUtils.stringToTime(viewModel.getTime().getValue()),
                viewModel.getFrequency().getValue(),
                1
        );
        viewModel.handleAddMySchedule(myScheduleRequest, true);

//        Intent intent = new Intent();
//        setResult(Activity.RESULT_OK, intent);
//        finish();
    }

    @Override
    public void handleEditSummitNotification() {
        MyScheduleRequest myScheduleRequest = new MyScheduleRequest(
                viewModel.getExercise().getValue(),
                DateUtils.stringToDate(viewModel.getStartDate().getValue()),
                DateUtils.stringToDate(viewModel.getEndDate().getValue()),
                TimeUtils.stringToTime(viewModel.getTime().getValue()),
                viewModel.getFrequency().getValue()
        );
        viewModel.handleAddMySchedule(myScheduleRequest, false);
//        Intent intent = new Intent();
//        setResult(Activity.RESULT_OK, intent);
//        finish();
    }

    private void initObserveDate() {
        viewModel.getEndDate().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String endDateValue) {
                String startDateValue = null;
                if(viewModel.getStartDate() !=null) startDateValue = viewModel.getStartDate().getValue();
                if (startDateValue != null && endDateValue != null) {
                    viewModel.getIsCheckEndDate().setValue(DateUtils.stringToDate(startDateValue).before(DateUtils.stringToDate(endDateValue)));
                }
            }
        });
        viewModel.getStartDate().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String startDateValue) {
                String endDateValue = null;
                if (viewModel.getEndDate() != null) endDateValue = viewModel.getEndDate().getValue();
                if (startDateValue != null && endDateValue != null) {
                    viewModel.getIsCheckEndDate().setValue(DateUtils.stringToDate(startDateValue).before(DateUtils.stringToDate(endDateValue)));
                }
            }
        });
    }

    private void openDialog(@NonNull Dialog dialog, String name, String message, int idStatus) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_dialog);
        Window window = dialog.getWindow();
        if(window == null) return;
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);
        dialog.setCancelable(false);
        TextView tvCancel = dialog.findViewById(R.id.dialog_cancel);
        TextView tvOk = dialog.findViewById(R.id.dialog_sure);
        TextView tvName = dialog.findViewById(R.id.dialog_text_name);
        TextView tvMessage = dialog.findViewById(R.id.dialog_text_message);
        tvMessage.setText(message);
        tvName.setText(name);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(idStatus == 1) {
                    dialog.dismiss();
                    viewModel.handleDeleteMySchedule();
                }else {
                    dialog.dismiss();
                }
            }
        });
    }


    private void openDialogSchedule(@NonNull Dialog dialog, String message) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_dialog_schedule);
        Window window = dialog.getWindow();
        if(window == null) return;

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);
        dialog.setCancelable(false);

        TextView tvMessage = dialog.findViewById(R.id.dialog_text_message);
        tvMessage.setText(message);
    }

    @Override
    public void handleDialogScheduleSuccess(String message) {
        final Dialog dialog = new Dialog(this);
        openDialogSchedule(dialog, message);
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        }, 3000);
    }
    @Override
    public void handleDialogScheduleFail(String message) {
        final Dialog dialog = new Dialog(this);
        openDialogSchedule(dialog, message);
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 3000);
    }
    @Override
    public void handleResult(){
        Intent intent = new Intent();
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

}