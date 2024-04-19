package vn.edu.ptit.planta.ui.plant.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.model.plant.Plant;
import vn.edu.ptit.planta.ui.plant.plantdetail.PlantDetailActivity;

public class PlantSearchAdapter extends RecyclerView.Adapter<PlantSearchAdapter.PlantSearchViewHolder> {
    private List<Plant> plants;
    private Context context;

    public PlantSearchAdapter(Context context) {
        this.context = context;
    }

    public void updateData(List<Plant> newData){
        this.plants = newData;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public PlantSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_plant, parent, false);
        return new PlantSearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantSearchViewHolder holder, int position) {
        Plant plant = plants.get(position);
        if (plant == null){
            return;
        }

        if(position == 0) holder.view.setVisibility(View.GONE);
        else holder.view.setVisibility(View.VISIBLE);

        holder.tvName.setText(plant.getName());
        holder.tvTypePlant.setText(plant.getTypePlant());

        Glide.with(context)
                .load(plant.getMainImage())
                .placeholder(R.drawable.icon_no_mob)
                .override(300,300)
                .into(holder.shapeableImageView);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlantDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("plant", plant);
                intent.putExtras(bundle);
                context.startActivity(intent);
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

    public class PlantSearchViewHolder extends  RecyclerView.ViewHolder{

        private RelativeLayout layout;
        private ShapeableImageView shapeableImageView;

        private View view;
        private TextView tvName, tvTypePlant;
        public PlantSearchViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.id_layout_plant);
            shapeableImageView = itemView.findViewById(R.id.circle_img_plant);
            tvName = itemView.findViewById(R.id.tv_name_plant);
            tvTypePlant = itemView.findViewById(R.id.tv_type_plant);
            view = itemView.findViewById(R.id.view_item_plant_search);
        }
    }

    public void release() {
        context = null;
    }
}
