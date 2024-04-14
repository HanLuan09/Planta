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
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.databinding.FragmentPlantBinding;
import vn.edu.ptit.planta.model.plant.Plant;
import vn.edu.ptit.planta.ui.plant.chooseplant.ChoosePlantActivity;
import vn.edu.ptit.planta.ui.plant.plantdetail.PlantDetailActivity;

public class PlantFragment extends Fragment implements PlantNavigator {

    private FragmentPlantBinding binding;
    private PlantViewModel viewModel;
    private RecyclerView recyclerView;
    private PlantAdapter plantAdapter;

    private boolean isLoading = false;
    private boolean isLastPage = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_plant, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(PlantViewModel.class);
        binding.setPlantViewModel(viewModel);
        binding.setLifecycleOwner(this);

        viewModel.setPlantNavigator(this);

        listPlantObserve();

        initRecyclerView();

        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initRecyclerView() {
        recyclerView = binding.idRcvPlant;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        
        viewModel.getListPlants().observe(requireActivity(), new Observer<List<Plant>>() {
            @Override
            public void onChanged(List<Plant> plants) {
                if (plantAdapter == null) {
                    // Nếu adapter chưa được tạo
                    plantAdapter = new PlantAdapter(plants);
                    plantAdapter.setPlantNavigator(PlantFragment.this);
                    recyclerView.setAdapter(plantAdapter);
                } else {
                    // Nếu adapter đã tồn tại, cập nhật dữ liệu mới
                    plantAdapter.updateData(plants);
                }
            }
        });
    }



    private void initRecyclerVi() {
        recyclerView = binding.idRcvPlant;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.addOnScrollListener(new PaginationScrollListener( gridLayoutManager ) {
            @Override
            public void loadMoreItems() {
                Log.e("Test Load", "Test ...    ");
                isLoading = true;
                loadNextPage();
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }
        });

    }

    private void loadNextPage(){
        viewModel.fetchData();
        listPlantObserve();
        isLoading = false;
    }

    private void listPlantObserve() {
        viewModel.getListPlants().observe(getViewLifecycleOwner(), new Observer<List<Plant>>() {
            @Override
            public void onChanged(List<Plant> plants) {
                if (plantAdapter == null) {
                    // Nếu adapter chưa được tạo
                    plantAdapter = new PlantAdapter(plants);
                    plantAdapter.setPlantNavigator(PlantFragment.this);
                    recyclerView.setAdapter(plantAdapter);
                } else {
                    // Nếu adapter đã tồn tại, cập nhật dữ liệu mới
                    plantAdapter.updateData(plants);
                }
            }
        });
    }

    @Override
    public void handlePlantDetail(Plant plant) {
        Intent intent = new Intent(requireContext(), PlantDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("plant", plant);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    @Override
    public void handlePlantSearch() {
        Intent intent = new Intent(requireContext(), ChoosePlantActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("is_search", true);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void glideImage(String image, ShapeableImageView shapeableImageView) {
        Glide.with(requireContext())
                .load(image)
                .placeholder(R.drawable.icon_no_mob)
                .override(300,300)
                .into(shapeableImageView);
    }
}