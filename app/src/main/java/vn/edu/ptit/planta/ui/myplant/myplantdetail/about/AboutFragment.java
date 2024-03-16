package vn.edu.ptit.planta.ui.myplant.myplantdetail.about;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.model.Test;

public class AboutFragment extends Fragment {

    private boolean isExpanded = false;
    private ImageView expandButton;
    private TextView textView;
    private RecyclerView recyclerView;
    private AboutAdapter aboutAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_about, container, false);

        recyclerView = view.findViewById(R.id.recyclerview_about);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        aboutAdapter = new AboutAdapter(getListPlant());
        recyclerView.setAdapter(aboutAdapter);
        return view;
    }
    private List<Test> getListPlant() {
        List<Test> plants = new ArrayList<>();
        plants.add(new Test("Hoa hướng dương", "https://cdn.tgdd.vn/Files/2021/08/03/1372812/dac-diem-nguon-goc-va-y-nghia-dac-biet-cua-hoa-huong-duong-202206031122479117.jpeg"));
        plants.add(new Test("Hoa hướng dương", "https://cdn.tgdd.vn/Files/2021/08/03/1372812/dac-diem-nguon-goc-va-y-nghia-dac-biet-cua-hoa-huong-duong-202206031122479117.jpeg"));
        plants.add(new Test("Hoa hướng dương", "https://cdn.tgdd.vn/Files/2021/08/03/1372812/dac-diem-nguon-goc-va-y-nghia-dac-biet-cua-hoa-huong-duong-202206031122479117.jpeg"));
        plants.add(new Test("Hoa hướng dương", "https://cdn.tgdd.vn/Files/2021/08/03/1372812/dac-diem-nguon-goc-va-y-nghia-dac-biet-cua-hoa-huong-duong-202206031122479117.jpeg"));

        return plants;
    }
}