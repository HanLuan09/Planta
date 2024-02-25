package vn.edu.ptit.planta.ui.customdialog;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

public interface TimeManagement {
    TimeManagement dialogDatePicker(DatePickerDialog.OnDateSetListener onDateSetListener);

    TimeManagement dialogDatePickerCustom(int cus, DatePickerDialog.OnDateSetListener onDateSetListener);

    void showDatePickerDialog();

    TimeManagement dialogTimePicker(TimePickerDialog.OnTimeSetListener onTimeSetListener);

    void showTimePickerDialog();
}

