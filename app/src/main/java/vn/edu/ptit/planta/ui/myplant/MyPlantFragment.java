package vn.edu.ptit.planta.ui.myplant;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.databinding.FragmentMyPlantBinding;
import vn.edu.ptit.planta.ui.myplant.myplantdetail.MyPlantDetailActivity;
import vn.edu.ptit.planta.ui.myplant.search.SearchMyPlantActivity;

public class MyPlantFragment extends Fragment implements MyPlantNavigator {

    private FragmentMyPlantBinding binding;
    private MyPlantViewModel viewModel;
    private RecyclerView recyclerView;
    private MyPlantAdapter myPlantAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_plant, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(MyPlantViewModel.class);
        binding.setMyPlantViewModel(viewModel);
        binding.setLifecycleOwner(this);

        viewModel.setMyPlantNavigator(this);

        initRecyclerView();

        return binding.getRoot();
    }

    private void initRecyclerView() {
        recyclerView = binding.idRcvMyplant;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        // Quan sát dữ liệu thay đổi
        viewModel.getListMyPlants().observe(requireActivity(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                if (myPlantAdapter == null) {
                    // Nếu adapter chưa được tạo
                    myPlantAdapter = new MyPlantAdapter(strings);
                    myPlantAdapter.setMyPlantNavigator(MyPlantFragment.this);
                    recyclerView.setAdapter(myPlantAdapter);
                } else {
                    // Nếu adapter đã tồn tại, cập nhật dữ liệu mới
                    myPlantAdapter.updateData(strings);
                }
            }
        });

    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void handleSearchMyPlant() {
        Intent intent = new Intent(requireContext(), SearchMyPlantActivity.class);
        startActivity(intent);
    }

    @Override
    public void handleMyPlantDetail() {
        Intent intent = new Intent(requireContext(), MyPlantDetailActivity.class);
        startActivity(intent);

    }
}