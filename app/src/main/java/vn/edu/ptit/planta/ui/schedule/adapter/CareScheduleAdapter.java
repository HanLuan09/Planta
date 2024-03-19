package vn.edu.ptit.planta.ui.schedule.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.model.care.CareSchedule;
import vn.edu.ptit.planta.ui.myplant.myplantdetail.MyPlantDetailActivity;

public class CareScheduleAdapter extends RecyclerView.Adapter<CareScheduleAdapter.CareScheduleViewHolder> {

    private List<CareSchedule> listCareSchedules;
    private Context mContext;

    public void setListCareSchedules(List<CareSchedule> listCareSchedules) {
        this.listCareSchedules = listCareSchedules;
        notifyDataSetChanged();
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CareScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_plant_schedule, parent, false);
        return new CareScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CareScheduleViewHolder holder, int position) {
        CareSchedule careSchedule = listCareSchedules.get(position);
        if(careSchedule == null) return;

        if(position == 0) holder.view.setVisibility(View.GONE);
        else holder.view.setVisibility(View.VISIBLE);

        holder.tvName.setText(careSchedule.getName());
        holder.tvTitle.setText(careSchedule.getTime());


        Glide.with(mContext)
                .load(careSchedule.getImage())
                .override(300,300)
                .into(holder.imageView);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MyPlantDetailActivity.class);

                Bundle bundle = new Bundle();
                bundle.putInt("id_myplant", careSchedule.getId());
                bundle.putString("name_myplant", careSchedule.getName());
                bundle.putString("image_myplant", careSchedule.getImage());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listCareSchedules != null) return listCareSchedules.size();
        return 0;
    }

    public class CareScheduleViewHolder extends RecyclerView.ViewHolder {

        private CheckBox checkBox;
        private ShapeableImageView imageView;
        private TextView tvName;
        private TextView tvTitle;
        private MaterialCardView cardView;

        private RelativeLayout relativeLayout;
        private View view;

        public CareScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkbox_item_my_plant_schedule);
            imageView = itemView.findViewById(R.id.circle_item_my_plant_schedule);
            tvName = itemView.findViewById(R.id.tv_name_item_my_plant_schedule);
            tvTitle = itemView.findViewById(R.id.tv_title_item_my_plant_schedule);
            cardView = itemView.findViewById(R.id.cardview_item_my_plant_schedule);
            relativeLayout = itemView.findViewById(R.id.click_item_my_plant_schedule);
            view = itemView.findViewById(R.id.view_item_my_plant_schedule);
        }
    }
}
