package vn.edu.ptit.planta.ui.note;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.data.RetrofitClient;
import vn.edu.ptit.planta.data.service.NoteService;
import vn.edu.ptit.planta.model.ApiResponse;

public class NoteFragment extends Fragment implements OnNoteAddedListener{

    private RecyclerView recyclerView;

//    private ListView listView;
    private NoteAdapter adapter;
    private NoteService noteService;

    int myPlantId = 0;

    // Khai báo một biến để lưu trữ tham chiếu đến NoteFragment
    private static NoteFragment instance;

    // Phương thức static để cung cấp tham chiếu đến NoteFragment
    public static NoteFragment getInstance() {
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
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        recyclerView = view.findViewById(R.id.listView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        // Khởi tạo NoteService
        noteService = RetrofitClient.getNoteService();
//
        Bundle bundle = getArguments();
        if (bundle != null) {
            myPlantId = bundle.getInt("id_myplant");
        }

        // Gọi phương thức để lấy danh sách ghi chú từ API
        fetchNotesByPlantId(myPlantId);


        FloatingActionButton addNoteButton = view.findViewById(R.id.addNote);
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddNoteDialog();
            }
        });

        return view;
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
                        List<Note> notes = apiResponse.getResult();
                        adapter = new NoteAdapter(getContext(), (ArrayList<Note>) notes);
                        recyclerView.setAdapter(adapter);
//                        Toast.makeText(getContext(), "Danh sách ghi chú đã được cập nhật!", Toast.LENGTH_SHORT).show();
                        // Hoặc ghi log
                        Log.d("NoteFragment", "Danh sách ghi chú đã được cập nhật!");
                    }

                } else {
                    // Xử lý trường hợp không thành công
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Note>>> call, Throwable t) {
                // Xử lý lỗi khi gọi API
                Toast.makeText(getContext(), "Không thể lấy danh sách ghi chú từ máy chủ!", Toast.LENGTH_SHORT).show();
                // Hoặc ghi log
                Log.e("NoteFragment", "Không thể lấy danh sách ghi chú từ máy chủ: " + t.getMessage());
            }

        });
    }


    private void openAddNoteDialog() {
        DialogNote dialogNote = new DialogNote();

        Bundle args = new Bundle();
        args.putInt("id_myplant1", myPlantId);
        dialogNote.setArguments(args);

        dialogNote.setOnNoteAddedListener(this);
        dialogNote.show(getChildFragmentManager(), "DialogNote");
    }

    @Override
    public void onNoteAdded() {
        fetchNotesByPlantId(myPlantId);
    }
}
