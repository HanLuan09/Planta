package vn.edu.ptit.planta.ui.home.myplant.myplantdetail.about;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.model.Test;

public class AboutAdapter extends RecyclerView.Adapter<AboutAdapter.AboutViewHolder> {

    private List<Test> listTests;

    public AboutAdapter(List<Test> listTests) {
        this.listTests = listTests;
    }

    @NonNull
    @Override
    public AboutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_information_plant, parent, false);
        return new AboutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AboutViewHolder holder, int position) {
        Test test = listTests.get(position);
        if(test == null) return;

        holder.tvName.setText(test.getName());
        holder.tvDesc.setText(test.getDesc());
        holder.layout_myplanta.setVisibility(View.GONE);
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.layout_myplanta.getVisibility() == View.GONE)
                    holder.layout_myplanta.setVisibility(View.VISIBLE);
                else holder.layout_myplanta.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(listTests != null) return listTests.size();
        return 0;
    }

    public class AboutViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvDesc;
        private LinearLayout layout_myplanta;
        public AboutViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDesc = itemView.findViewById(R.id.tv_content_name_myplanta);
            tvName = itemView.findViewById(R.id.tv_name_myplanta);
            layout_myplanta = itemView.findViewById(R.id.layout_content_myplanta);
        }
    }
}
