package vn.edu.ptit.planta.ui.calendarmyplant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.databinding.ActivityCalendarMyPlantBinding;

import android.view.View;
import android.widget.TextView;

import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar;

public class CalendarMyPlantActivity extends AppCompatActivity implements CalendarMyPlantNavigator {

    private ActivityCalendarMyPlantBinding binding;
    private CalendarMyPlantViewModel viewModel;
    private CollapsibleCalendar collapsibleCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_calendar_my_plant);

        viewModel = new ViewModelProvider(this).get(CalendarMyPlantViewModel.class);

        binding.setCalendarMyPlantViewModel(viewModel);
        binding.setLifecycleOwner(this);

        viewModel.setCalendarMyPlantNavigator(this);


        collapsibleCalendar = binding.collapsibleCalendarView;

//        collapsibleCalendar.setExpandIconVisible(true);
//        Calendar today = new GregorianCalendar();
//        collapsibleCalendar.addEventTag(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH));
//        today.add(Calendar.DATE, 1);
//        collapsibleCalendar.setSelectedDay(new Day(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH)));
//        collapsibleCalendar.addEventTag(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), Color.BLUE);
//        collapsibleCalendar.setParams(new CollapsibleCalendar.Params(0, 100));
        collapsibleCalendar.setCalendarListener(new CollapsibleCalendar.CalendarListener() {
            @Override
            public void onDayChanged() {

            }

            @Override
            public void onClickListener() {
                if (collapsibleCalendar.getExpanded()) {
                    collapsibleCalendar.collapse(400);
                } else {
                    collapsibleCalendar.expand(400);
                }
            }

            @Override
            public void onDaySelect() {
                TextView text = findViewById(R.id.calendar_text_view);
                int day = collapsibleCalendar.getSelectedDay().getDay();
                int month = collapsibleCalendar.getSelectedDay().getMonth()+1;
                int year = collapsibleCalendar.getSelectedDay().getYear();

                text.setText("Ngày " +day+"/"+month+"/"+ year);
            }

            @Override
            public void onItemClick(View v) {

            }

            @Override
            public void onDataUpdate() {

            }

            @Override
            public void onMonthChange() {

            }

            @Override
            public void onWeekChange(int position) {

            }
        });
    }

    @Override
    public void handelBlack() {
        finish();
    }
}