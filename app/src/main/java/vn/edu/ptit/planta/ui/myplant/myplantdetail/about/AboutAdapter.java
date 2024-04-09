package vn.edu.ptit.planta.ui.myplant.myplantdetail.about;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.model.AttributeOfMyPlant;
import vn.edu.ptit.planta.model.myplant.MyPlant;
import vn.edu.ptit.planta.model.Test;

public class AboutAdapter extends RecyclerView.Adapter<AboutAdapter.AboutViewHolder> {

    private List<AttributeOfMyPlant> attributeOfMyPlants;

    public AboutAdapter(List<AttributeOfMyPlant> attributeOfMyPlants) {
        this.attributeOfMyPlants = attributeOfMyPlants;
    }
    public void updateData(List<AttributeOfMyPlant> newData) {
        this.attributeOfMyPlants = newData;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public AboutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_information_plant, parent, false);
        return new AboutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AboutViewHolder holder, int position) {
        AttributeOfMyPlant attributeOfMyPlant = attributeOfMyPlants.get(position);
        if(attributeOfMyPlant == null) return;

        holder.tvTitle.setText(attributeOfMyPlant.getTitle());
        holder.tvContent.setText(attributeOfMyPlant.getContent());
        holder.layout_content_myplanta.setVisibility(View.GONE);

        holder.layout_expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expand(holder.layout_content_myplanta, holder.checkBox);
            }
        });
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expand(holder.layout_content_myplanta, holder.checkBox);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(attributeOfMyPlants != null) return attributeOfMyPlants.size();
        return 0;
    }

    public void expand(LinearLayout layout, CheckBox checkBox){
        if(layout.getVisibility() == View.GONE) {
            layout.setVisibility(View.VISIBLE);
            checkBox.setChecked(true);
        }
        else {
            layout.setVisibility(View.GONE);
            checkBox.setChecked(false);
        }
    }

    public class AboutViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvContent;
        private LinearLayout layout_content_myplanta, layout_expand;
        private CheckBox checkBox;
        public AboutViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title_myplanta);
            tvContent = itemView.findViewById(R.id.tv_content_myplanta);
            layout_expand = itemView.findViewById(R.id.layout_expand);
            layout_content_myplanta = itemView.findViewById(R.id.layout_content_myplanta);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }
}
