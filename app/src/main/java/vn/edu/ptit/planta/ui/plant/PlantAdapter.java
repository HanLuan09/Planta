package vn.edu.ptit.planta.ui.plant;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

import vn.edu.ptit.planta.R;

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.PlantViewHolder> {

    private List<String> listPlants;

    private PlantNavigator plantNavigator;

    public PlantAdapter(List<String> listMyPlants) {
        this.listPlants = listMyPlants;
    }

    public void setPlantNavigator(PlantNavigator navigator) {
        this.plantNavigator = navigator;
    }

    public void updateData(List<String> newData) {
        this.listPlants = newData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plant, parent, false);
        return new PlantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantViewHolder holder, int position) {
        String s = listPlants.get(position);
        if(s == null) return;
        holder.textViewName.setText(s);
        holder.shapeableImageView.setImageResource(R.drawable.plant_img);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(plantNavigator != null) plantNavigator.handleMyPlantDetail();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listPlants != null) return listPlants.size();
        return 0;
    }

    public class PlantViewHolder extends RecyclerView.ViewHolder {
        private ShapeableImageView shapeableImageView;
        private TextView textViewName;
        private CardView cardView;
        public PlantViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.id_cardview_plant);
            shapeableImageView = itemView.findViewById(R.id.circle_img_planta);
            textViewName = itemView.findViewById(R.id.tv_name_planta);
//            textViewDesc = itemView.findViewById(R.id.tv_desc_myplanta);
        }
    }
}
