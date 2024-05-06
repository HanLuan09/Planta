package vn.edu.ptit.planta.ui.note;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.data.RetrofitClient;
import vn.edu.ptit.planta.data.service.NoteService;
import vn.edu.ptit.planta.model.ApiResponse;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.viewHolderInfo>{

    Context context;

    List<Note> noteList;

    public NoteAdapter(Context context, ArrayList<Note> Notelist) {
        this.context = context;
        this.noteList = Notelist;
    }

    @NonNull
    @Override
    public NoteAdapter.viewHolderInfo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_note, parent, false); //tao 1 view moi
        return new viewHolderInfo(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.viewHolderInfo holder, @SuppressLint("RecyclerView") int position) {
        Note list = noteList.get(position);
        holder.tvNote.setText("Note số " + position);

        String date = list.getDate();
        holder.tvDate.setText(date);


        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete note");
                builder.setIcon(R.drawable.warning);
                builder.setMessage("Are you sure");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int noteId = noteList.get(position).getId();
                        RetrofitClient.getNoteService().deleteNoteById(noteId).enqueue(new Callback<ApiResponse<Void>>() {
                            @Override
                            public void onResponse(Call<ApiResponse<Void>> call, Response<ApiResponse<Void>> response) {
                                if (response.isSuccessful()) {
                                    // Xóa ghi chú thành công
                                    Toast.makeText(context, "Note deleted successfully", Toast.LENGTH_SHORT).show();
                                    // Sau khi xóa thành công, cập nhật lại danh sách ghi chú
                                    NoteFragment.getInstance().onNoteAdded();
                                    ChartFragment.getInstance().OnUpdate();
                                } else {
                                    // Xử lý khi xóa không thành công
                                    Toast.makeText(context, "Failed to delete note", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ApiResponse<Void>> call, Throwable t) {
                                // Xử lý lỗi khi gọi API
                                Toast.makeText(context, "Failed to delete note", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        holder.imgUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note note = noteList.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("description", note.getDescription());
                bundle.putString("date", note.getDate());
                bundle.putInt("flowers", note.getFlowers());
                bundle.putInt("fruits", note.getFruits());
                bundle.putInt("height", note.getHeight());
                bundle.putString("img", note.getImage());
                bundle.putInt("id", note.getId());

                UpdateNote updateNote = new UpdateNote();
                updateNote.setArguments(bundle);
                ChartFragment.getInstance().OnUpdate();
                NoteFragment.getInstance().onNoteAdded();

                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                updateNote.show(fragmentManager, "UpdateNote");
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }



    public class viewHolderInfo extends RecyclerView.ViewHolder {
        TextView tvNote, tvDate;
        ImageView imgUpdate, imgDelete;

        public viewHolderInfo(@NonNull View itemView) {
            super(itemView);
            tvNote = itemView.findViewById(R.id.tvNote);
            tvDate = itemView.findViewById(R.id.tvDate);
            imgUpdate = itemView.findViewById(R.id.imgUpdate);
            imgDelete = itemView.findViewById(R.id.imgDelete);
        }
    }


}
