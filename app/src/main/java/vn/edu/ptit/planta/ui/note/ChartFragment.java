package vn.edu.ptit.planta.ui.note;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

import vn.edu.ptit.planta.R;

public class ChartFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart, container, false);

        // Khởi tạo biểu đồ
        LineChart lineChart = view.findViewById(R.id.lineChart);

        // Khởi tạo dữ liệu cho chiều cao thực và chiều cao tiêu chuẩn
        ArrayList<Entry> actualHeightEntries = new ArrayList<>();
        ArrayList<Entry> standardHeightEntries = new ArrayList<>();

        // Thêm dữ liệu cho chiều cao thực
        actualHeightEntries.add(new Entry(1, 160));
        actualHeightEntries.add(new Entry(2, 163));
        actualHeightEntries.add(new Entry(3, 165));
        actualHeightEntries.add(new Entry(4, 168));

        // Thêm dữ liệu cho chiều cao tiêu chuẩn
        standardHeightEntries.add(new Entry(1, 155));
        standardHeightEntries.add(new Entry(2, 157));
        standardHeightEntries.add(new Entry(3, 160));
        standardHeightEntries.add(new Entry(4, 162));

        // Tạo DataSet cho từng loại dữ liệu
        LineDataSet actualHeightDataSet = new LineDataSet(actualHeightEntries, "Chiều cao thực");
        LineDataSet standardHeightDataSet = new LineDataSet(standardHeightEntries, "Chiều cao tiêu chuẩn");

        // Tạo dữ liệu cho biểu đồ
        LineData lineData = new LineData(actualHeightDataSet, standardHeightDataSet);

        // Thiết lập các thuộc tính cho DataSet và biểu đồ
        actualHeightDataSet.setColor(Color.BLUE);
        standardHeightDataSet.setColor(Color.RED);

        // Tùy chỉnh trục X
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        // Thiết lập dữ liệu cho biểu đồ
        lineChart.setData(lineData);

        // Hiển thị biểu đồ
        lineChart.invalidate();

        return view;
    }
}
