package vn.edu.ptit.planta.ui.schedule.notification;

import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.databinding.BottomSheetFrequencyBinding;
import vn.edu.ptit.planta.ui.home.HomeViewModel;

public class FrequencyBottomSheet extends BottomSheetDialogFragment {

    private BottomSheetFrequencyBinding binding;
    private AddNotificationViewModel viewModel;
    private Button buttonDialog;
    private NumberPicker numberPicker;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.bottom_sheet_frequency, null, false);
        viewModel = new ViewModelProvider(requireActivity()).get(AddNotificationViewModel.class);
        binding.setNotificationViewModel(viewModel);
        binding.setLifecycleOwner(this);

        bottomSheetDialog.setContentView(binding.getRoot());

        buttonDialog = binding.dialogSure;
        numberPicker = binding.numberFrequency;

        int checkDialog = 0;
        if(viewModel.getCheckDialog() != null) checkDialog = viewModel.getCheckDialog().getValue();

        if(checkDialog == 1) {
            showDialogFrequency();
        }else if(checkDialog == 2) {
            showDialogPeriod();
        }

        return bottomSheetDialog;
    }

    private void showDialogFrequency() {
        binding.dialogText.setText("Tần suất");
        numberPicker.setMaxValue(100);
        numberPicker.setMinValue(1);
        if(viewModel.getFrequency() != null && viewModel.getFrequency().getValue()!= null) {
            numberPicker.setValue(viewModel.getFrequency().getValue());
        }
        buttonDialog.setText("Nhắc lại sau " + numberPicker.getValue() + " ngày");
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                buttonDialog.setText("Nhắc lại sau " + newVal + " ngày");
            }
        });

        buttonDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int frequency = numberPicker.getValue();
                viewModel.getFrequency().setValue(frequency);
                dismiss();
            }
        });

    }

    private void showDialogPeriod() {
        binding.dialogText.setText("Khoảng thời gian");
        numberPicker.setMaxValue(365);
        numberPicker.setMinValue(30);
        if(viewModel.getFrequency() != null && viewModel.getFrequency().getValue()!= null) {
            numberPicker.setValue(viewModel.getFrequency().getValue());
        }
        buttonDialog.setText("Khoảng thời gian " + numberPicker.getValue() + " ngày");
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                buttonDialog.setText("Nhắc lại sau " + newVal + " ngày");
            }
        });

        buttonDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int frequency = numberPicker.getValue();
                viewModel.getFrequency().setValue(frequency);
                dismiss();
            }
        });

    }
}
