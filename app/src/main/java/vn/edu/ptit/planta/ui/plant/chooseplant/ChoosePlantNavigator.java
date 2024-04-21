package vn.edu.ptit.planta.ui.plant.chooseplant;

import com.google.android.material.imageview.ShapeableImageView;

import vn.edu.ptit.planta.model.plant.Plant;

public interface ChoosePlantNavigator {
    public void handleBack();
    public void handleChoosePlant(Plant plant);
    public void glideImage(String image, ShapeableImageView shapeableImageView);
}
