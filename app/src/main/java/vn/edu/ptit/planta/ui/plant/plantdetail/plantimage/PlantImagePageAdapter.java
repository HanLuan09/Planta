package vn.edu.ptit.planta.ui.plant.plantdetail.plantimage;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

import vn.edu.ptit.planta.model.plant.PlantImage;

public class PlantImagePageAdapter extends FragmentStateAdapter {

    private List<PlantImage> listPlantImages;

    public PlantImagePageAdapter(@NonNull FragmentActivity fragmentActivity, List<PlantImage> listPlantImages) {
        super(fragmentActivity);
        this.listPlantImages = listPlantImages;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(listPlantImages == null || listPlantImages.isEmpty()) return null;
        PlantImage plantImage = listPlantImages.get(position);
        PlantImageItemFragment fragment = new PlantImageItemFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("image", plantImage);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return listPlantImages != null ? listPlantImages.size() : 0;
    }
}
