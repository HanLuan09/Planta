package vn.edu.ptit.planta.ui.mygarden;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.databinding.ActivityAddMyGardenBinding;
import vn.edu.ptit.planta.ui.MainActivity;
import vn.edu.ptit.planta.ui.mygarden.notification.AddNotificationActivity;

public class AddMyGardenActivity extends AppCompatActivity implements MyGardenNavigator {

    private ActivityAddMyGardenBinding binding;

    private MyGardenViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_my_garden);

        viewModel = new ViewModelProvider(this).get(MyGardenViewModel.class);

        binding.setMyGardenViewModel(viewModel);
        binding.setLifecycleOwner(this);
        viewModel.setMyGardenNavigator(this);

        observeLoginChanges();
    }

    @Override
    public void handleAddNotification() {
        Intent intent = new Intent(this, AddNotificationActivity.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
    }

    @Override
    public void handleBlackMyPlant() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
        finish();
    }

    private void observeLoginChanges() {
        viewModel.getNamePlant().observe(this, newNamePlant -> updateValidation());
    }
    private void updateValidation() {
        viewModel.getValidateGarden().setValue(viewModel.getNamePlant() != null &&
                !viewModel.getNamePlant().getValue().trim().isEmpty());
    }
}