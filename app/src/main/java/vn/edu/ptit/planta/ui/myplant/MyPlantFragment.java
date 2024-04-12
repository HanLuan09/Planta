package vn.edu.ptit.planta.ui.myplant;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.databinding.FragmentMyPlantBinding;
import vn.edu.ptit.planta.model.myplant.MyPlant;
import vn.edu.ptit.planta.ui.myplant.myplantdetail.MyPlantDetailActivity;
import vn.edu.ptit.planta.ui.myplant.search.SearchMyPlantActivity;

public class MyPlantFragment extends Fragment implements MyPlantNavigator {
    private FragmentMyPlantBinding binding;
    private MyPlantViewModel myPlantViewModel;
    private RecyclerView recyclerView;
    private MyPlantAdapter myPlantAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_plant, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("User", Context.MODE_PRIVATE);
        int idUser = sharedPreferences.getInt("idUser",0);

        myPlantViewModel = new ViewModelProvider(this).get(MyPlantViewModel.class);

        binding.setMyPlantViewModel(myPlantViewModel);
        binding.setLifecycleOwner(this);

        myPlantViewModel.setMyPlantNavigator(this);
        myPlantViewModel.setIdUserResponse(idUser);

        initRecyclerView();

        return binding.getRoot();
    }

    private void initRecyclerView() {
        recyclerView = binding.idRcvMyplant;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        // Quan sát dữ liệu thay đổi
        myPlantViewModel.getListMyPlants().observe(requireActivity(), new Observer<List<MyPlant>>() {
            @Override
            public void onChanged(List<MyPlant> myPlants) {
                if (myPlantAdapter == null) {
                    // Nếu adapter chưa được tạo
                    myPlantAdapter = new MyPlantAdapter(myPlants);
                    myPlantAdapter.setMyPlantNavigator(MyPlantFragment.this);
                    recyclerView.setAdapter(myPlantAdapter);
                } else {
                    // Nếu adapter đã tồn tại, cập nhật dữ liệu mới
                    myPlantAdapter.updateData(myPlants);
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
    public void handleMyPlantDetail(MyPlant myPlant) {
        Intent intent = new Intent(requireContext(), MyPlantDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("myplant",myPlant);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    @Override
    public void glideImage(String image, ShapeableImageView shapeableImageView) {
        if (image != null) {
            byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

            Glide.with(requireContext())
                    .load(bitmap)
                    .placeholder(R.drawable.icon_no_mob)
                    .override(70, 70)
                    .into(shapeableImageView);
        } else {
            shapeableImageView.setImageResource(R.drawable.icon_no_image);
        }
    }
}