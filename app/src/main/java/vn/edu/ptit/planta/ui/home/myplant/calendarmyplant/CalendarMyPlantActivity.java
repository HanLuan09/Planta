package vn.edu.ptit.planta.ui.home.myplant.calendarmyplant;

import android.os.Bundle;
import android.widget.CalendarView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.databinding.ActivityCalendarMyPlantBinding;

public class CalendarMyPlantActivity extends AppCompatActivity {

    private ActivityCalendarMyPlantBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding =  DataBindingUtil.setContentView(this, R.layout.activity_calendar_my_plant);

        binding.calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // Xử lý sự kiện click ở đây
                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                binding.calendarTextView.setText(selectedDate);
            }
        });

    }

}