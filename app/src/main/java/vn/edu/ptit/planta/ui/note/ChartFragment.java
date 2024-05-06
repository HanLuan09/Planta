package vn.edu.ptit.planta.ui.note;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.data.RetrofitClient;
import vn.edu.ptit.planta.data.service.NoteService;
import vn.edu.ptit.planta.model.ApiResponse;

public class ChartFragment extends Fragment implements OnNoteUpdate{
    private LineChart lineChart;

    private NoteService noteService;
    private Button btnFlowers, btnFruits, btnHeight;
    int myPlantId = 0;

    int k = 0;

    List<Note> notes = new ArrayList<>();

    SampleResponse sampleResponse;


    private static ChartFragment instance;

    // Phương thức static để cung cấp tham chiếu đến NoteFragment
    public static ChartFragment getInstance() {
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart, container, false);

        lineChart = view.findViewById(R.id.lineChart);
        btnFlowers = view.findViewById(R.id.btnFlowers);
        btnFruits = view.findViewById(R.id.btnFruits);
        btnHeight = view.findViewById(R.id.btnHeight);

        Bundle bundle = getArguments();
        if (bundle != null) {
            myPlantId = bundle.getInt("id_myplant");
        }

        noteService = RetrofitClient.getNoteService();

        fetchNotesByPlantId(myPlantId);

        fetchSampleByPlantId(myPlantId);

        DialogNote dialogNote = new DialogNote();
        dialogNote.setOnNoteUpdateListener(this);


        btnFlowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hiển thị dữ liệu hoa trên biểu đồ
                displayFlowersData();
            }
        });

        btnFruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hiển thị dữ liệu quả trên biểu đồ
                displayFruitsData();
            }
        });

        btnHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hiển thị dữ liệu chiều cao trên biểu đồ
                displayHeightData();
            }
        });

        // Mặc định hiển thị dữ liệu chiều cao khi mở fragment
//        displayHeightData();

        return view;
    }


    private void displayFlowersData() {

        ArrayList<Entry> flowers = new ArrayList<>();
        ArrayList<Entry> sample = new ArrayList<>();
        int count = 0;
        for(Note note: notes){
            count++;
            flowers.add(new Entry(count,note.getFlowers()));
        }

        String flower = sampleResponse.getFlowers();

        String[] array = flower.split(",");
        int count1 = 0;

        for (int i = 0; i < flowers.size(); i++){
            count1++;
            sample.add(new Entry(count1,Integer.parseInt(array[i])));
        }

        // Tạo DataSet cho chiều cao thực
        LineDataSet FlowersDataSet = new LineDataSet(flowers, "Số hoa thực");
        LineDataSet SampleDataSet = new LineDataSet(sample, "Số hoa mẫu");

        // Tạo dữ liệu cho biểu đồ
        LineData lineData = new LineData(FlowersDataSet, SampleDataSet);

        // Thiết lập các thuộc tính cho DataSet và biểu đồ
        FlowersDataSet.setColor(Color.BLUE);
        SampleDataSet.setColor(Color.RED);

        // Thiết lập dữ liệu cho biểu đồ
        lineChart.setData(lineData);

        // Hiển thị biểu đồ
        lineChart.invalidate();
    }

    private void displayFruitsData() {
        ArrayList<Entry> fruits = new ArrayList<>();
        ArrayList<Entry> sample = new ArrayList<>();
        int count = 0;
        for(Note note: notes){
            count++;
            fruits.add(new Entry(count,note.getFruits()));
        }

        String fruit = sampleResponse.getFruits();

        String[] array = fruit.split(",");
        int count1 = 0;

        for (int i = 0; i < fruits.size(); i++){
            count1++;
            sample.add(new Entry(count1,Integer.parseInt(array[i])));
        }

        // Tạo DataSet cho chiều cao thực
        LineDataSet FruitsDataSet = new LineDataSet(fruits, "Số quả thực");
        LineDataSet SampleDataSet = new LineDataSet(sample, "Số quả mẫu");

        // Tạo dữ liệu cho biểu đồ
        LineData lineData = new LineData(FruitsDataSet, SampleDataSet);

        // Thiết lập các thuộc tính cho DataSet và biểu đồ
        FruitsDataSet.setColor(Color.BLUE);
        SampleDataSet.setColor(Color.RED);

        // Thiết lập dữ liệu cho biểu đồ
        lineChart.setData(lineData);

        // Hiển thị biểu đồ
        lineChart.invalidate();
    }

    private void displayHeightData() {
        ArrayList<Entry> heights = new ArrayList<>();
        ArrayList<Entry> sample = new ArrayList<>();
        int count = 0;
        for(Note note: notes){
            count++;
            heights.add(new Entry(count,note.getHeight()));
        }

        String height = sampleResponse.getHeight();

        String[] array = height.split(",");
        int count1 = 0;

        for (int i = 0; i < heights.size(); i++){
            count1++;
            sample.add(new Entry(count1,Integer.parseInt(array[i])));
        }

        // Tạo DataSet cho chiều cao thực
        LineDataSet HeightsDataSet = new LineDataSet(heights, "Số quả thực");
        LineDataSet SampleDataSet = new LineDataSet(sample, "Số quả mẫu");

        // Tạo dữ liệu cho biểu đồ
        LineData lineData = new LineData(HeightsDataSet, SampleDataSet);

        // Thiết lập các thuộc tính cho DataSet và biểu đồ
        HeightsDataSet.setColor(Color.BLUE);
        SampleDataSet.setColor(Color.RED);

        // Thiết lập dữ liệu cho biểu đồ
        lineChart.setData(lineData);

        // Hiển thị biểu đồ
        lineChart.invalidate();
    }


    private void fetchNotesByPlantId(int plantId) {
        // Gọi API để lấy danh sách ghi chú dựa trên id của cây
        noteService.getAllNotesByMyPlantId(plantId).enqueue(new Callback<ApiResponse<List<Note>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Note>>> call, Response<ApiResponse<List<Note>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<List<Note>> apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getResult() != null) {
                        // Cập nhật danh sách ghi chú mới
                        notes = apiResponse.getResult();
                    }

                } else {
                    // Xử lý trường hợp không thành công
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Note>>> call, Throwable t) {
                // Xử lý trường hợp không thành công
            }

        });
    }


    private void fetchSampleByPlantId(int plantId) {
        // Gọi API để lấy danh sách ghi chú dựa trên id của cây
        noteService.getSampleByMyPlantId(plantId).enqueue(new Callback<ApiResponse<SampleResponse>>() {


            @Override
            public void onResponse(Call<ApiResponse<SampleResponse>> call, Response<ApiResponse<SampleResponse>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<SampleResponse> apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getResult() != null) {
                        // Cập nhật danh sách ghi chú mới
                        sampleResponse = apiResponse.getResult();
                    }

                }
            }

            @Override
            public void onFailure(Call<ApiResponse<SampleResponse>> call, Throwable throwable) {
            }
        });
    }


    @Override
    public void OnUpdate() {
        fetchNotesByPlantId(myPlantId);

        fetchSampleByPlantId(myPlantId);

    }
}
