package vn.edu.ptit.planta.ui.home.today;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import vn.edu.ptit.planta.R;

public class TodayAdapter extends RecyclerView.Adapter<TodayAdapter.TodayViewHolder> {

    @NonNull
    @Override
    public TodayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plant, parent, false);
        return new TodayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodayViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class TodayViewHolder extends RecyclerView.ViewHolder {

        public TodayViewHolder(@NonNull View itemView) {
            super(itemView);
//            cardView = itemView.findViewById(R.id.id_cardview_plant);
//            shapeableImageView = itemView.findViewById(R.id.circle_img_planta);
//            textViewName = itemView.findViewById(R.id.tv_name_planta);
//            textViewDesc = itemView.findViewById(R.id.tv_desc_myplanta);
        }
    }
}
