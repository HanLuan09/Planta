package vn.edu.ptit.planta.ui.myplant;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
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
import vn.edu.ptit.planta.model.myplant.MyPlant;

public class MyPlantAdapter extends RecyclerView.Adapter<MyPlantAdapter.MyPlantViewHolder> {

    private List<MyPlant> listMyPlants;

    private MyPlantNavigator myPlantNavigator;

    public MyPlantAdapter(List<MyPlant> listMyPlants) {
        this.listMyPlants = listMyPlants;
    }

    public void setMyPlantNavigator(MyPlantNavigator navigator) {
        this.myPlantNavigator = navigator;
    }

    public void updateData(List<MyPlant> newData) {
        this.listMyPlants = newData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyPlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_plant, parent, false);
        return new MyPlantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPlantViewHolder holder, int position) {
        MyPlant myPlant = listMyPlants.get(position);
        if(myPlant == null) return;

        holder.textViewName.setText(myPlant.getName());
        holder.textViewDesc.setText(myPlant.getGrownDate());
        myPlantNavigator.glideImage(myPlant.getImage(),holder.shapeableImageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myPlantNavigator != null) myPlantNavigator.handleMyPlantDetail(myPlant);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listMyPlants != null) return listMyPlants.size();
        return 0;
    }

    public class MyPlantViewHolder extends RecyclerView.ViewHolder {
        private ShapeableImageView shapeableImageView;
        private TextView textViewName;
        private TextView textViewDesc;
        private CardView cardView;
        public MyPlantViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.id_cardview_my_plant);
            shapeableImageView = itemView.findViewById(R.id.circle_img_myplanta);
            textViewName = itemView.findViewById(R.id.tv_name_myplanta);
            textViewDesc = itemView.findViewById(R.id.tv_desc_myplanta);
        }
    }
}
