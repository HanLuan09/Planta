package vn.edu.ptit.planta.ui.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;

import vn.edu.ptit.planta.R;

import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import com.shrikanthravi.collapsiblecalendarview.data.Day;
import com.shrikanthravi.collapsiblecalendarview.view.OnSwipeTouchListener;
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class TestActivity extends AppCompatActivity {

    private CollapsibleCalendar collapsibleCalendar;
    private ScheduleViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        viewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);

    }
}