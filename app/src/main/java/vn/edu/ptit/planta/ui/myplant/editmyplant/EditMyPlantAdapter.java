package vn.edu.ptit.planta.ui.myplant.editmyplant;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.model.AttributeOfMyPlant;

public class EditMyPlantAdapter extends RecyclerView.Adapter<EditMyPlantAdapter.EditMyPlantViewHolder> {

    private List<AttributeOfMyPlant> attributeOfMyPlants;
    public EditMyPlantAdapter(List<AttributeOfMyPlant> attributeOfMyPlants){
        this.attributeOfMyPlants = attributeOfMyPlants;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EditMyPlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_edit_information_plant, parent, false);
        return new EditMyPlantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EditMyPlantViewHolder holder, int position) {
        AttributeOfMyPlant attributeOfMyPlant = attributeOfMyPlants.get(position);
        if ((attributeOfMyPlant == null)) return;

        holder.tvTitle.setText(attributeOfMyPlant.getTitle());
        holder.etContent.setText(attributeOfMyPlant.getContent());
    }

    @Override
    public int getItemCount() {
        if(attributeOfMyPlants != null) return attributeOfMyPlants.size();
        return 0;
    }

    public class EditMyPlantViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private EditText etContent;
        public EditMyPlantViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title_myplanta);
            etContent = itemView.findViewById(R.id.et_content_myplanta);
        }
    }
}
