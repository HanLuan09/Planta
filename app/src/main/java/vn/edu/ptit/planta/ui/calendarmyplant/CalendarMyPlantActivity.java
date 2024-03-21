package vn.edu.ptit.planta.ui.calendarmyplant;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.databinding.ActivityCalendarMyPlantBinding;
import vn.edu.ptit.planta.model.care.CareSchedule;
import vn.edu.ptit.planta.model.care.CareScheduleCategory;
import vn.edu.ptit.planta.ui.schedule.adapter.CareScheduleCategoryAdapter;

import android.view.View;
import android.widget.TextView;

import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar;

import java.util.ArrayList;
import java.util.List;

public class CalendarMyPlantActivity extends AppCompatActivity implements CalendarMyPlantNavigator {

    private ActivityCalendarMyPlantBinding binding;
    private CalendarMyPlantViewModel viewModel;
    private CollapsibleCalendar collapsibleCalendar;
    private RecyclerView recyclerView;
    private CareScheduleCategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_calendar_my_plant);

        viewModel = new ViewModelProvider(this).get(CalendarMyPlantViewModel.class);

        binding.setCalendarMyPlantViewModel(viewModel);
        binding.setLifecycleOwner(this);

        viewModel.setCalendarMyPlantNavigator(this);


        collapsibleCalendar = binding.collapsibleCalendarView;

        recyclerView = binding.rcvCalendarMyPlant;
        adapter = new CareScheduleCategoryAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter.setListCareScheduleCategorys(getListCareScheduleCategorys());
        recyclerView.setAdapter(adapter);

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
    protected void onDestroy() {
        super.onDestroy();
        if(adapter != null) adapter.release();
    }

    @Override
    public void handelBlack() {
        finish();
    }

    @NonNull
    private List<CareScheduleCategory> getListCareScheduleCategorys() {
        List<CareScheduleCategory> careScheduleCategories = new ArrayList<>();
        List<CareSchedule> careSchedules = new ArrayList<>();
        careSchedules.add(new CareSchedule(1, "Hoa hướng dương", "9:00"));
        careSchedules.add(new CareSchedule(2, "Cây ngô", "8:00"));
        careSchedules.add(new CareSchedule(3, "Hoa cẩm tú cầu", "18:00"));

        careScheduleCategories.add(new CareScheduleCategory("Tưới cây", careSchedules));

        careSchedules = new ArrayList<>();
        careSchedules.add(new CareSchedule(1, "Hoa hồng", "9:00"));
        careSchedules.add(new CareSchedule(2, "Cây vạn niên thanh", "16:00"));
        careScheduleCategories.add(new CareScheduleCategory("Bón phân", careSchedules));

        careSchedules = new ArrayList<>();
        careSchedules.add(new CareSchedule(1, "Hoa hướng dương", "9:00"));
        careScheduleCategories.add(new CareScheduleCategory("Thu hoạch", careSchedules));


        careSchedules = new ArrayList<>();
        careSchedules.add(new CareSchedule(1, "Hoa hồng", "9:00"));
        careSchedules.add(new CareSchedule(2, "Hoa cẩm tú cầu hoa tú cầu Hoa cẩm tú cầu hoa tú cầu", "8:00"));
        careSchedules.add(new CareSchedule(3, "Hoa cẩm tú cầu hoa tú cầu", "18:00"));
        careScheduleCategories.add(new CareScheduleCategory("Cắt tỉa cây", careSchedules));
        return careScheduleCategories;
    }
}