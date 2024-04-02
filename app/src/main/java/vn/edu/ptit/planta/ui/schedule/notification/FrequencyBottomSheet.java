package vn.edu.ptit.planta.ui.schedule.notification;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

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
            showDialogExercise();
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

    private void showDialogExercise() {
        binding.dialogText.setText("Bài tập");
        RadioGroup radioGroup = binding.dialogRadioGroup;
        RadioButton rbWater = binding.dialogRadioButtonWater;
        RadioButton rbFertilizer = binding.dialogRadioButtonFertilizer;
        RadioButton rbOther = binding.dialogRadioButtonOther;

        String text = viewModel.getExercise().getValue().trim();

        if(text.equals("Tưới nước")){
            rbWater.setChecked(true);
        }else if(text.equals("Bón phân")) {
            rbFertilizer.setChecked(true);
        }else{
            rbOther.setChecked(true);
            binding.textExercise.setText(viewModel.getExercise().getValue());
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = binding.getRoot().findViewById(checkedId);
                if (radioButton != null) {

                    if(rbWater.isChecked()){

                    }else if(rbFertilizer.isChecked()){
                        Toast.makeText(requireContext(), "Bạn đã chọn nước 2" , Toast.LENGTH_LONG).show();
                    }
                    if(rbOther.isChecked()){

                        binding.textExercise.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if (s.toString().trim().isEmpty()) {
                                    binding.dialogBtExercise.setEnabled(false);
                                } else {
                                    binding.dialogBtExercise.setEnabled(true);
                                }
                            }
                        });
                    }else {
                        binding.dialogBtExercise.setEnabled(true);
                    }
                }
            }
        });

        binding.dialogBtExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text;
                if(rbWater.isChecked()) { text = rbWater.getText().toString().trim();}
                else if(rbFertilizer.isChecked()) { text = rbFertilizer.getText().toString().trim();}
                else if(rbOther.isChecked()) { text = setString(binding.textExercise.getText().toString().trim());}
                else { text = "";}

                if(text.isEmpty() || text =="") {
                    Toast.makeText(requireContext(), "Bạn cần nhập tên bài tập!" , Toast.LENGTH_LONG).show();
                }else{
                    viewModel.getExercise().setValue(text);
                    dismiss();
                }
            }
        });
        
    }

    @NonNull
    private String setString(@NonNull String s) {
        if(s.isEmpty() || s == "") return "";
        String[] src = s.split("\\s+");
        String kq= src[0].substring(0, 1).toUpperCase()+ src[0].substring(1).toLowerCase()+ " ";
        for(int i=1; i< src.length; i++) {
            kq +=src[i].toLowerCase() + " ";
        }
        return kq.trim();
    }
}
