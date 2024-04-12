package vn.edu.ptit.planta.ui.plant.chooseplant;

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
import vn.edu.ptit.planta.model.plant.Plant;

public class ChoosePlantAdapter extends RecyclerView.Adapter<ChoosePlantAdapter.AddPlantViewHolder>{

    private List<Plant> plants;
    private ChoosePlantNavigator choosePlantNavigator;
    public ChoosePlantAdapter(List<Plant> plants) {
        this.plants = plants;
    }

    public void setChoosePlantNavigator(ChoosePlantNavigator choosePlantNavigator) {
        this.choosePlantNavigator = choosePlantNavigator;
    }

    public void updateData(List<Plant> newData){
        this.plants = newData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AddPlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_choose_my_plant, parent, false);
        return new AddPlantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddPlantViewHolder holder, int position) {
        Plant plant = plants.get(position);
        if (plant == null){
            return;
        }

        holder.tvName.setText(plant.getName());
        holder.tvTypePlant.setText(plant.getTypePlant());
        choosePlantNavigator.glideImage(plant.getMainImage(), holder.shapeableImageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(choosePlantNavigator != null) choosePlantNavigator.handleChoosePlant(plant);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(plants != null){
            return plants.size();
        }
        return 0;
    }

    public class AddPlantViewHolder extends  RecyclerView.ViewHolder{

        private CardView cardView;
        private ShapeableImageView shapeableImageView;
        private TextView tvName, tvTypePlant;
        public AddPlantViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.id_cardview_plant);
            shapeableImageView = itemView.findViewById(R.id.circle_img_plant);
            tvName = itemView.findViewById(R.id.tv_name_plant);
            tvTypePlant = itemView.findViewById(R.id.tv_type_plant);
        }
    }
}
