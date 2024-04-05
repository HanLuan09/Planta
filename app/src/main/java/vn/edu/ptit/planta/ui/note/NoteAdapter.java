package vn.edu.ptit.planta.ui.note;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.ptit.planta.R;

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
    public void onBindViewHolder(@NonNull NoteAdapter.viewHolderInfo holder, int position) {
        Note list = noteList.get(position);
        holder.tvNote.setText("Note số " + list.getId());
        holder.tvDate.setText(list.getDate());

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int id = list.get(holder.getAdapterPosition()).getId();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete note");
                builder.setIcon(R.drawable.warning);
                builder.setMessage("Are you sure");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        boolean check = taskInfoDAO.removeInfo(id);
//                        if (check) {
//                            Toast.makeText(context.getApplicationContext(), "Delete successful", Toast.LENGTH_SHORT).show();
//                            list.clear();
//                            list = taskInfoDAO.getListInfo();
//                            notifyItemRemoved(holder.getAdapterPosition());
//                        } else {
//                            Toast.makeText(context.getApplicationContext(), "Delete fail", Toast.LENGTH_SHORT).show();
//                        }
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
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                View view1 = inflater.inflate(R.layout.dialog_note, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(view1);

                final EditText edDes, edDate, edFlower, edFruit, edHeight;
                edDes = view1.findViewById(R.id.edDes);
                edDate = view1.findViewById(R.id.edDate);
                edFlower = view1.findViewById(R.id.edFlower);
                edFruit = view1.findViewById(R.id.edFruit);
                edHeight = view1.findViewById(R.id.edHeight);

//                //load data len alertdialog
                edDes.setText(list.getDescription());
                edDate.setText(list.getDate());
                edFlower.setText(list.getFlowers());
                edFruit.setText(list.getFruits());
                edHeight.setText(list.getHeight());


                builder.setTitle("Update note");
                builder.setIcon(R.drawable.update);
                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        TaskInfo info = new TaskInfo();
//                        info.setId(Integer.parseInt(edID.getText().toString().trim()));
//                        info.setTitle(edTitle.getText().toString().trim());
//                        info.setContent(edContent.getText().toString().trim());
//                        info.setDate(edDate.getText().toString().trim());
//                        info.setType(edType.getText().toString().trim());
//
//                        long check = taskInfoDAO.updateInfo(info);
//                        if (check < 0) {
//                            Toast.makeText(context.getApplicationContext(), "Update Fail", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(context.getApplicationContext(), "Update successful", Toast.LENGTH_SHORT).show();
//                        }
//
//                        list.set(position, info);
//                        notifyItemChanged(holder.getAdapterPosition());
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
