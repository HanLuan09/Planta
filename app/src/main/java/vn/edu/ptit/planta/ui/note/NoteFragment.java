package vn.edu.ptit.planta.ui.note;

import static android.content.Intent.getIntent;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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

public class NoteFragment extends Fragment {

    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private NoteService noteService;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Khởi tạo NoteService
        noteService = RetrofitClient.getNoteService();
        int myPlantId = 0;
        Bundle bundle = getArguments();
        if (bundle != null) {
            myPlantId = bundle.getInt("id_myplant");
            //viewModel.getIdMyPlant().setValue(myPlantId);
        }


        // Gọi phương thức để lấy danh sách ghi chú từ API
        fetchNotesByPlantId(myPlantId);
        Toast.makeText(getContext(), "Id đây " + myPlantId, Toast.LENGTH_SHORT).show();


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
                        updateNotesList(notes);
                        Toast.makeText(getContext(), "Danh sách ghi chú đã được cập nhật!", Toast.LENGTH_SHORT).show();
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


    private void updateNotesList(List<Note> notes) {
        // Cập nhật danh sách ghi chú vào adapter
        adapter = new NoteAdapter(getContext(), (ArrayList<Note>) notes);
        recyclerView.setAdapter(adapter);
    }

    private void openAddNoteDialog() {
        DialogNote dialogNote = new DialogNote();
        dialogNote.show(getChildFragmentManager(), "DialogNote");
    }
}
