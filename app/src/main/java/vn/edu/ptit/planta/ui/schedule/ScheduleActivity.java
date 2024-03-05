package vn.edu.ptit.planta.ui.schedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.databinding.ActivityScheduleBinding;
import vn.edu.ptit.planta.ui.MainActivity;
import vn.edu.ptit.planta.ui.schedule.notification.AddNotificationActivity;

public class ScheduleActivity extends AppCompatActivity implements ScheduleNavigator {

    private ActivityScheduleBinding binding;

    private ScheduleViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_schedule);

        viewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);

        binding.setScheduleViewModel(viewModel);
        binding.setLifecycleOwner(this);
        viewModel.setScheduleNavigator(this);
    }

    @Override
    public void handleAddNotification() {
        Intent intent = new Intent(this, AddNotificationActivity.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
    }

    @Override
    public void handleBlackNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
        finish();
    }

}