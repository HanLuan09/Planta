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
import vn.edu.ptit.planta.model.care.CareCalendar;
import vn.edu.ptit.planta.ui.myplant.myplantdetail.care.CareNavigator;

public class CareScheduleCalendarAdapter extends RecyclerView.Adapter<CareScheduleCalendarAdapter.CareScheduleCalendarViewHolder> {

    private List<CareCalendar> listCareScheduleCalendars;

    private CareNavigator careNavigator;

    public void setListCareScheduleCalendars(List<CareCalendar> listCareScheduleCalendars) {
        this.listCareScheduleCalendars = listCareScheduleCalendars;
        notifyDataSetChanged();
    }

    public void setCareNavigator(CareNavigator careNavigator) {
        this.careNavigator = careNavigator;
    }

    @NonNull
    @Override
    public CareScheduleCalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule_note, parent, false);
        return new CareScheduleCalendarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CareScheduleCalendarViewHolder holder, int position) {

        CareCalendar careCalendar = listCareScheduleCalendars.get(position);
        if(careCalendar == null) return;
        holder.tvName.setText(careCalendar.getName());

        if(careCalendar.getName().trim().equals("Tưới nước")) {
            holder.imageView.setImageResource(R.drawable.ic_water);
        }else if(careCalendar.getName().trim().equals("Bón phân")) {
            holder.imageView.setImageResource(R.drawable.ico_fertilize);
        }else {
            holder.imageView.setImageResource(R.drawable.ic_plant_fill);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(careCalendar != null) careNavigator.handleNotificationDetail(careCalendar);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listCareScheduleCalendars != null) return listCareScheduleCalendars.size();
        return 0;
    }

    public class CareScheduleCalendarViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private ImageView imageView;

        private MaterialCardView cardView;
        public CareScheduleCalendarViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name_care);
            imageView = itemView.findViewById(R.id.img_item_noti);
            cardView = itemView.findViewById(R.id.id_cv_care_note);
        }
    }
}
