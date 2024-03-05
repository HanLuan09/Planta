package vn.edu.ptit.planta.ui.home.myplant.myplantdetail.care.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.model.care.CategoryCalendar;

public class CareCategoryCalendarAdapter extends RecyclerView.Adapter<CareCategoryCalendarAdapter.CareCategoryCalendarViewHolder> {

    private List<CategoryCalendar> listCategoryCalendar;
    @NonNull
    @Override
    public CareCategoryCalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule_care, parent, false);
        return new CareCategoryCalendarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CareCategoryCalendarViewHolder holder, int position) {
        CategoryCalendar categoryCalendar = listCategoryCalendar.get(position);

        if (categoryCalendar == null) return;

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        if(listCategoryCalendar != null) return listCategoryCalendar.size();
        return 0;
    }

    public class CareCategoryCalendarViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView tvMonth;
        private TextView tvDay;
        public CareCategoryCalendarViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.care_calendar);
            tvDay = itemView.findViewById(R.id.tv_day);
            tvMonth = itemView.findViewById(R.id.tv_month);

        }
    }
}
