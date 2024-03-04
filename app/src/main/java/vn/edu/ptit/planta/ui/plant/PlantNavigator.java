package vn.edu.ptit.planta.ui.plant;

import com.google.android.material.imageview.ShapeableImageView;

import vn.edu.ptit.planta.model.Plant;

public interface PlantNavigator {

    public void handlePlantDetail(Plant plant);
    public void glideImage(String image, ShapeableImageView shapeableImageView);
}
