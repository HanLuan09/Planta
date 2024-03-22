package vn.edu.ptit.planta.ui.myplant.myplantdetail.about;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.model.AttributeOfMyPlant;
import vn.edu.ptit.planta.model.MyPlant;

public class AboutFragment extends Fragment {
    private RecyclerView recyclerView;
    private AboutAdapter aboutAdapter;
    private LinearLayout layoutExpandImageMyPlant, layoutContentImageMyPlant;
    private ImageView ivImageMyPlant;
    private CheckBox cbImageMyPlant;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_about, container, false);

        recyclerView = view.findViewById(R.id.recyclerview_about);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        MyPlant myPlant = getMyPlant();
        List<AttributeOfMyPlant> attributeOfMyPlants = getListAttributeOfMyPlant(myPlant);
        aboutAdapter = new AboutAdapter(attributeOfMyPlants);
        recyclerView.setAdapter(aboutAdapter);

        layoutExpandImageMyPlant = view.findViewById(R.id.layout_expand_image);
        layoutContentImageMyPlant = view.findViewById(R.id.layout_content_myplanta);
        ivImageMyPlant = view.findViewById(R.id.iv_image_myplanta);
        cbImageMyPlant = view.findViewById(R.id.checkbox);

        Glide.with(requireContext())
                .load(myPlant.getImage())
                .placeholder(R.drawable.icon_no_image)
                .into(ivImageMyPlant);
        layoutContentImageMyPlant.setVisibility(View.GONE);
        layoutExpandImageMyPlant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expand(layoutContentImageMyPlant, cbImageMyPlant);
            }
        });
        cbImageMyPlant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expand(layoutContentImageMyPlant, cbImageMyPlant);
            }
        });
        return view;
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
    private MyPlant getMyPlant() {
        return new MyPlant(1, "Hoa hướng dương", "https://cdn.tgdd.vn/Files/2021/08/03/1372812/dac-diem-nguon-goc-va-y-nghia-dac-biet-cua-hoa-huong-duong-202206031122479117.jpeg","Sáng", "2024/03/21");
    }
    private List<AttributeOfMyPlant> getListAttributeOfMyPlant(MyPlant myPlant) {
        List<AttributeOfMyPlant> attributeOfMyPlants = new ArrayList<>();
        attributeOfMyPlants.add(new AttributeOfMyPlant("Tên thực vật",myPlant.getName()));
        attributeOfMyPlants.add(new AttributeOfMyPlant("Ngày trồng",myPlant.getGrownDate()));
        attributeOfMyPlants.add(new AttributeOfMyPlant("Loại ánh sáng",myPlant.getKindOfLight()));
//        attributeOfMyPlants.add(new AttributeOfMyPlant("Kích thước trưởng thành",myPlant.getName()));
//        attributeOfMyPlants.add(new AttributeOfMyPlant("Thời gian trưởng thành",myPlant.getName()));
//        attributeOfMyPlants.add(new AttributeOfMyPlant("Mô tả",myPlant.getName()));

        return attributeOfMyPlants;
    }
}