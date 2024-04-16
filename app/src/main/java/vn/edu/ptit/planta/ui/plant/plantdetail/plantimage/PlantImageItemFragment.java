package vn.edu.ptit.planta.ui.plant.plantdetail.plantimage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.model.plant.PlantImage;

public class PlantImageItemFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_plant_image_item, container, false);

        Bundle bundleImage = getArguments();
        ImageView imageView = view.findViewById(R.id.plant_item_image);
        if(bundleImage != null) {
            PlantImage plantImage = (PlantImage) bundleImage.getSerializable("image");
            Glide.with(requireContext())
                    .load(plantImage.getImage())
                    .placeholder(R.drawable.icon_no_mob)
                    .override(300,300)
                    .into(imageView);
        }

        return view;
    }
}