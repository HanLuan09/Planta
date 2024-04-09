package vn.edu.ptit.planta.ui.calendarmyplant;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.databinding.ActivityCalendarMyPlantBinding;
import vn.edu.ptit.planta.model.care.CareSchedule;
import vn.edu.ptit.planta.model.care.CareScheduleResponse;
import vn.edu.ptit.planta.ui.schedule.adapter.CareScheduleCategoryAdapter;
import vn.edu.ptit.planta.utils.DateUtils;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
        viewModel.initData();

        collapsibleCalendar = binding.collapsibleCalendarView;

        recyclerView = binding.rcvCalendarMyPlant;
        adapter = new CareScheduleCategoryAdapter(this);
        adapter.setActivityResultLauncher(mActivityResultLauncher);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        viewModel.getListCareSchedules().observe(this, new Observer<List<CareScheduleResponse>>() {
            @Override
            public void onChanged(List<CareScheduleResponse> careSchedules) {
                if (careSchedules != null) {
                    setAdapterSchedules();
                }
            }
        });
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

                String selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d",year,month,day);
                viewModel.getDaySelect().setValue(selectedDate);
                viewModel.getDaySelect().observe(CalendarMyPlantActivity.this, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        viewModel.getListCareSchedules().setValue(viewModel.myPlantToDayByUser(viewModel.getListMyPlantSchedules().getValue(), s));
                        setAdapterSchedules();
                    }
                });

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

    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == Activity.RESULT_OK){
                viewModel.initData();
                setAdapterSchedules();
            }
        }
    });
    private void setAdapterSchedules() {
        adapter.setListCareScheduleCategorys(viewModel.getListCareSchedules().getValue());
        adapter.setSelectDate(viewModel.getDaySelect().getValue());
        recyclerView.setAdapter(adapter);
    }
}