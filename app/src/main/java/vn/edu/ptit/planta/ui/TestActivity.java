package vn.edu.ptit.planta.ui;

import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        
//        TextView textView = findViewById(R.id.tv_date);

        collapsibleCalendar = findViewById(R.id.collapsibleCalendarView);

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
}