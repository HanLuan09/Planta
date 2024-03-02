package vn.edu.ptit.planta.ui.plant;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.databinding.FragmentPlantBinding;
import vn.edu.ptit.planta.ui.TestActivity;

public class PlantFragment extends Fragment implements PlantNavigator {

    private FragmentPlantBinding binding;
    private PlantViewModel viewModel;
    private RecyclerView recyclerView;
    private PlantAdapter plantAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_plant, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(PlantViewModel.class);
        binding.setPlantViewModel(viewModel);
        binding.setLifecycleOwner(this);

        viewModel.setPlantNavigator(this);

        initRecyclerView();

        ///
        binding.idPlantSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), TestActivity.class);
                startActivity(intent);
            }
        });
        ///

        return binding.getRoot();
    }

    private void initRecyclerView() {
        recyclerView = binding.idRcvPlant;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        // Quan sát dữ liệu thay đổi
        viewModel.getListPlants().observe(requireActivity(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                if (plantAdapter == null) {
                    // Nếu adapter chưa được tạo
                    plantAdapter = new PlantAdapter(strings);
                    plantAdapter.setPlantNavigator(PlantFragment.this);
                    recyclerView.setAdapter(plantAdapter);
                } else {
                    // Nếu adapter đã tồn tại, cập nhật dữ liệu mới
                    plantAdapter.updateData(strings);
                }
            }
        });

    }
}