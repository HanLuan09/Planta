package vn.edu.ptit.planta.ui.myplant.myplantdetail.care.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import vn.edu.ptit.planta.R;

public class CareScheduleAdapter extends RecyclerView.Adapter<CareScheduleAdapter.CareScheduleViewHolder> {

    @NonNull
    @Override
    public CareScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule_care, parent, false);
        return new CareScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CareScheduleViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class CareScheduleViewHolder extends RecyclerView.ViewHolder {

        public CareScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
