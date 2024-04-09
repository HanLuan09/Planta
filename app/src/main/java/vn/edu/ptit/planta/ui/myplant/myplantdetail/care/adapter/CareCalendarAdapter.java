package vn.edu.ptit.planta.ui.myplant.myplantdetail.care.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.Contract;

import java.util.List;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.model.care.CareCalendar;
import vn.edu.ptit.planta.model.care.CareCalendarResponse;
import vn.edu.ptit.planta.utils.DateUtils;

public class CareCalendarAdapter extends RecyclerView.Adapter<CareCalendarAdapter.CareCategoryCalendarViewHolder> {

    private List<CareCalendarResponse> listCareCalendars;
    private OnItemClickListener mListener;

    private int selectedItemPos = 0;
    private boolean checkPos = true;
    private int lastItemSelectedPos = 0;

    public CareCalendarAdapter(List<CareCalendarResponse> listCareCalendars) {
        this.listCareCalendars = listCareCalendars;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void updateCareCalendars(List<CareCalendarResponse> listCareCalendars) {
        this.listCareCalendars = listCareCalendars;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(List<CareCalendar> schedules, String date);
    }
    @NonNull
    @Override
    public CareCategoryCalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule_care, parent, false);
        return new CareCategoryCalendarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CareCategoryCalendarViewHolder holder, int position) {
        CareCalendarResponse careCalendar = listCareCalendars.get(position);

        if (careCalendar == null) return;
        String date = DateUtils.formatToDDMMYYYY(careCalendar.getDateCare());
        String[] dateParts = date.split("-");
        String newMonth = setMonth(dateParts[1]);

        if (checkPos && mListener != null) {
            mListener.onItemClick(careCalendar.getCareCalendars(), date);
            checkPos = false;
        }
        ViewGroup.LayoutParams layoutParams = holder.cardView.getLayoutParams();
        if(position == 0) {
            holder.tvDay.setText("Hôm nay");
            holder.tvMonth.setVisibility(View.GONE);
            layoutParams.width = 254;
            initClickCardView(holder, careCalendar, date);
        }else {
            holder.tvDay.setText(dateParts[0]);
            holder.tvMonth.setVisibility(View.VISIBLE);
            holder.tvMonth.setText(newMonth);
            layoutParams.width = 154;
            initClickCardView(holder, careCalendar, date);
        }
        holder.cardView.setLayoutParams(layoutParams);

        // Set background drawable based on selection state
        initSetBackground(holder, position);
    }

    @Override
    public int getItemCount() {
        return listCareCalendars != null ? listCareCalendars.size() : 0;
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

    private void initClickCardView(@NonNull CareCategoryCalendarViewHolder holder, CareCalendarResponse careCalendar, String date) {
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Update selected item positions
                lastItemSelectedPos = selectedItemPos;
                selectedItemPos = holder.getAdapterPosition();

                // Notify adapter of item changes
                notifyItemChanged(lastItemSelectedPos);
                notifyItemChanged(selectedItemPos);
                if (mListener != null) {
                    mListener.onItemClick(careCalendar.getCareCalendars(), date);
                }

            }
        });
    }
    private void initSetBackground(@NonNull CareCategoryCalendarViewHolder holder, int position) {
        int unSelection = ContextCompat.getColor(holder.itemView.getContext(), R.color.colorblackText);
        int selection = ContextCompat.getColor(holder.itemView.getContext(), R.color.colorPrimary);
        int text = ContextCompat.getColor(holder.itemView.getContext(), R.color.white);
        int unText = ContextCompat.getColor(holder.itemView.getContext(), R.color.colorGreenText);
        if (selectedItemPos == position) {
            // Set background drawable for selected item
            holder.cardView.setCardBackgroundColor(selection);
            holder.tvMonth.setTextColor(text);
            holder.tvDay.setTextColor(text);
        } else {
            holder.cardView.setCardBackgroundColor(unSelection);
            holder.tvMonth.setTextColor(unText);
            holder.tvDay.setTextColor(unText);
        }
    }

    @Nullable
    @Contract(pure = true)
    private String setMonth(@NonNull String month) {
        switch (month) {
            case "01":
                return "JAN";
            case "02":
                return "FEB";
            case "03":
                return "MAR";
            case "04":
                return "APR";
            case "05":
                return "MAY";
            case "06":
                return "JUN";
            case "07":
                return "JUL";
            case "08":
                return "AUG";
            case "09":
                return "SEP";
            case "10":
                return "OCT";
            case "11":
                return "NOV";
            case "12":
                return "DEC";
        }
        return null;
    }
}
