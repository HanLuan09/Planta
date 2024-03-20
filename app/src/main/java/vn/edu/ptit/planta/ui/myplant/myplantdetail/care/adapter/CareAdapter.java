package vn.edu.ptit.planta.ui.myplant.myplantdetail.care.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.model.ScheduleMyPlant;
import vn.edu.ptit.planta.ui.myplant.myplantdetail.care.CareNavigator;

public class CareAdapter extends RecyclerView.Adapter<CareAdapter.CareViewHolder>  {

    private List<ScheduleMyPlant> listSchedules;
    private CareNavigator careNavigator;

    public CareAdapter(List<ScheduleMyPlant> listSchedules) {
        this.listSchedules = listSchedules;
    }

    public void setCareNavigator(CareNavigator careNavigator) {
        this.careNavigator = careNavigator;
    }
    public void updateData(List<ScheduleMyPlant> newData) {
        this.listSchedules = newData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CareViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_care, parent, false);
        return new CareViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CareViewHolder holder, int position) {
        ScheduleMyPlant schedule = listSchedules.get(position);
        if(schedule == null) return;
        holder.tvName.setText(schedule.getName());
        if(schedule.getName().equals("Tưới nước")) {
            holder.imageView.setImageResource(R.drawable.ic_water);
        }else if(schedule.getName().equals("Bón phân")) {
            holder.imageView.setImageResource(R.drawable.ico_fertilize);
        }else {
            holder.imageView.setImageResource(R.drawable.icon_pl_leaf);
        }
        holder.tvDesc.setText("Định kỳ 2 ngày một lần");
        holder.tvTime.setText("Vào lúc: " + schedule.getDesc());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(careNavigator != null) careNavigator.handleEditNotification(schedule);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listSchedules != null) return listSchedules.size();
        return 0;
    }

    public class CareViewHolder extends RecyclerView.ViewHolder {

        private MaterialCardView cardView;
        private ImageView imageView;
        private TextView tvName;
        private TextView tvDesc;
        private TextView tvTime;
        public CareViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.id_cardview_care);
            imageView = itemView.findViewById(R.id.img_item_noti);
            tvName = itemView.findViewById(R.id.tv_name_care);
            tvDesc = itemView.findViewById(R.id.tv_desc_care);
            tvTime = itemView.findViewById(R.id.tv_time_care);
        }
    }
}
