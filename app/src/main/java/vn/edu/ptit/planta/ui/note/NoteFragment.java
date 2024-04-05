package vn.edu.ptit.planta.ui.note;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import vn.edu.ptit.planta.R;

public class NoteFragment extends Fragment {

    Context context;

    NoteAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Khởi tạo danh sách mẫu (ví dụ)
        ArrayList<Note> notes = new ArrayList<>();
        notes.add(new Note("1", "This is note 1", "2024-03-24", "3", "3", "20cm"));
        notes.add(new Note("2", "This is note 2", "2024-03-25", "5", "5", "25cm"));
        notes.add(new Note("3", "This is note 3", "2024-03-26", "3", "3", "30cm"));
        notes.add(new Note("4", "This is note 4", "2024-03-27", "2", "1", "30cm"));
        // Thêm các mục khác nếu cần

        adapter = new NoteAdapter(getContext(), notes); // Truyền dữ liệu vào adapter
        recyclerView.setAdapter(adapter);


        FloatingActionButton fab = view.findViewById(R.id.addNote);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = requireActivity().getLayoutInflater();
                View view1 = inflater.inflate(R.layout.dialog_note, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setView(view1);

                final EditText edDes, edDate, edFlower, edFruit, edHeight;
                edDes = view1.findViewById(R.id.edDes);
                edDate = view1.findViewById(R.id.edDate);
                edFlower = view1.findViewById(R.id.edFlower);
                edFruit = view1.findViewById(R.id.edFruit);
                edHeight = view1.findViewById(R.id.edHeight);

                builder.setTitle("Add Note");
                builder.setIcon(R.drawable.update);
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Xử lý khi người dùng nhấn nút Add
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Xử lý khi người dùng nhấn nút Cancel
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return view;
    }
}