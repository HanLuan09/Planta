package vn.edu.ptit.planta.ui.schedule;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.databinding.ActivityScheduleBinding;
import vn.edu.ptit.planta.model.myschedule.MySchedule;
import vn.edu.ptit.planta.ui.MainActivity;
import vn.edu.ptit.planta.ui.myplant.myplantdetail.care.CareFragment;
import vn.edu.ptit.planta.ui.myplant.myplantdetail.care.adapter.CareAdapter;
import vn.edu.ptit.planta.ui.schedule.adapter.ScheduleAdapter;
import vn.edu.ptit.planta.ui.schedule.notification.AddNotificationActivity;

public class ScheduleActivity extends AppCompatActivity implements ScheduleNavigator {

    private ActivityScheduleBinding binding;

    private ScheduleViewModel viewModel;

    private RecyclerView recyclerView;

    private ScheduleAdapter scheduleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_schedule);

        viewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);

        binding.setScheduleViewModel(viewModel);
        binding.setLifecycleOwner(this);
        viewModel.setScheduleNavigator(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int myPlantId = bundle.getInt("my_plant_id_page_add");
            viewModel.getIdMyPlant().setValue(myPlantId);

            Glide.with(this)
                    .load(bundle.getString("my_plant_image_page_add"))
                    .placeholder(R.drawable.icon_no_mob)
                    .override(300,300)
                    .into(binding.circleImgMyplanta);
        }

        if(viewModel.getIdMyPlant()!= null && viewModel.getIdMyPlant().getValue()!= null) {
            viewModel.initDataSchedule();
        }

        initOnBackPressedDispatcher();

        initRecyclerViewSchedule();
    }

    private void initOnBackPressedDispatcher() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            private boolean isDialogShown = false;
            @Override
            public void handleOnBackPressed() {
                if (!isDialogShown) {
                    if(viewModel.getIsCheckBlack() != null && viewModel.getIsCheckBlack().getValue() != null) showToast(viewModel.getIsCheckBlack().getValue()? "Ấn phím back thêm lần nữa để hoàn thành lịch trình" : "Ấn phím back thêm lần nữa để bỏ qua lịch trình");
                    else showToast("Ấn phím back thêm lần nữa để bỏ qua lịch trình");
                } else {
                    Intent intent = new Intent(ScheduleActivity.this, MainActivity.class);
                    startActivity(intent);
                    ScheduleActivity.this.overridePendingTransition(0, 0);
                    finish();
                }
            }

            private void showToast(String message) {
                Toast toast = new Toast(ScheduleActivity.this);
                View view = getLayoutInflater().inflate(R.layout.layout_custom_toast, (ViewGroup) findViewById(R.id.layout_custom_toast));
                TextView tvMessage = view.findViewById(R.id.toast_text_message);
                TextView tvTitle = view.findViewById(R.id.toast_text_title);
                tvTitle.setVisibility(View.GONE);
                tvMessage.setText(message);
                toast.setView(view);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.show();
                isDialogShown = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isDialogShown = false;
                    }
                }, 3000);
            }
        });
    }

    private void initRecyclerViewSchedule() {
        recyclerView = binding.rcvSchedule;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        setAdapterSchedules();
    }

    private void setAdapterSchedules() {
        viewModel.getListSchedules().observe(this, new Observer<List<MySchedule>>() {
            @Override
            public void onChanged(List<MySchedule> schedules) {
                if (scheduleAdapter == null) {
                    scheduleAdapter = new ScheduleAdapter(schedules);
                    scheduleAdapter.setScheduleNavigator(ScheduleActivity.this);
                    recyclerView.setAdapter(scheduleAdapter);
                } else {
                    scheduleAdapter.updateData(schedules);
                }

                if(schedules.size() > 0) viewModel.getIsCheckBlack().setValue(true);
                else viewModel.getIsCheckBlack().setValue(false);

                Log.e("Test baab ", viewModel.getIsCheckBlack().getValue().toString());
            }
        });
    }

    @Override
    public void handleAddNotification() {
        Intent intent = new Intent(this, AddNotificationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("care_my_plant_id", viewModel.getIdMyPlant().getValue());
        intent.putExtras(bundle);
        mActivityResultLauncher.launch(intent);
        this.overridePendingTransition(0, 0);
    }

    @Override
    public void handleBlackNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
        finish();
    }

    @Override
    public void handleEditNotification(MySchedule schedule) {
        Intent intent = new Intent(this, AddNotificationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("schedule_care", schedule);
        bundle.putInt("care_id", 1);
        bundle.putInt("care_my_plant_id", viewModel.getIdMyPlant().getValue());
        intent.putExtras(bundle);
        mActivityResultLauncher.launch(intent);
        this.overridePendingTransition(0, 0);
    }
    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == Activity.RESULT_OK){
                viewModel.initDataSchedule();
                setAdapterSchedules();
            }
        }
    });

}