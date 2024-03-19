package vn.edu.ptit.planta.ui.schedule.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.model.care.CareScheduleCategory;

public class CareScheduleCategoryAdapter extends RecyclerView.Adapter<CareScheduleCategoryAdapter.CareScheduleCategoryViewHolder> {

    private List<CareScheduleCategory> listCareScheduleCategorys;
    private Context mContext;

    public CareScheduleCategoryAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setListCareScheduleCategorys(List<CareScheduleCategory> listCareScheduleCategorys) {
        this.listCareScheduleCategorys = listCareScheduleCategorys;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CareScheduleCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_plant_schedule_category, parent, false);
        return new CareScheduleCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CareScheduleCategoryViewHolder holder, int position) {

        CareScheduleCategory category = listCareScheduleCategorys.get(position);
        if(category == null) return;
        holder.textView.setText(category.getExercise());

        if(category.getExercise().trim().equals("Tưới cây")) holder.imageView.setImageResource(R.drawable.ic_water);
        else if(category.getExercise().trim().equals("Bón phân")) holder.imageView.setImageResource(R.drawable.ico_fertilize);
        else holder.imageView.setImageResource(R.drawable.icon_pl_leaf);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        holder.recyclerView.setLayoutManager(linearLayoutManager);

        CareScheduleAdapter careScheduleAdapter = new CareScheduleAdapter();
        careScheduleAdapter.setListCareSchedules(category.getCareSchedules());
        careScheduleAdapter.setmContext(mContext);

        holder.recyclerView.setAdapter(careScheduleAdapter);

    }

    @Override
    public int getItemCount() {
        if(listCareScheduleCategorys != null) return  listCareScheduleCategorys.size();
        return 0;
    }

    public class CareScheduleCategoryViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private ImageView imageView;
        private RecyclerView recyclerView;
        public CareScheduleCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_my_plant_schedule_category);
            imageView = itemView.findViewById(R.id.iv_my_plant_schedule_category);
            textView = itemView.findViewById(R.id.tv_my_plant_schedule_category);
            recyclerView = itemView.findViewById(R.id.rcv_my_plant_schedule_category);
        }
    }

    public void release() {
        mContext = null;
    }
}
