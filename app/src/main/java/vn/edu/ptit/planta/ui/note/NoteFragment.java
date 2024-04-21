package vn.edu.ptit.planta.ui.note;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import vn.edu.ptit.planta.R;

public class NoteFragment extends Fragment {

    private static final int REQUEST_IMAGE_CAPTURE = 141;

    NoteAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<Note> notes = new ArrayList<>();
        notes.add(new Note("1", "This is note 1", "2024-03-24", "3", "3", "20cm"));
        notes.add(new Note("2", "This is note 2", "2024-03-25", "5", "5", "25cm"));
        notes.add(new Note("3", "This is note 3", "2024-03-26", "3", "3", "30cm"));
        notes.add(new Note("4", "This is note 4", "2024-03-27", "2", "1", "30cm"));

        adapter = new NoteAdapter(getContext(), notes);
        recyclerView.setAdapter(adapter);

        FloatingActionButton addNoteButton = view.findViewById(R.id.addNote);

        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddNoteDialog();
            }
        });

        return view;
    }

    private void openAddNoteDialog() {
        DialogNote dialogNote = new DialogNote();
        dialogNote.show(getChildFragmentManager(), "DialogNote");
    }
}
