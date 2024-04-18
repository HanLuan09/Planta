package vn.edu.ptit.planta.ui.myplant.search;

import com.google.android.material.imageview.ShapeableImageView;

import vn.edu.ptit.planta.model.myplant.MyPlant;

public interface SearchMyPlantNavigator {
    public void handleBack();
    public void handleMyPlantDetail(MyPlant myPlant);
    public void glideImage(String image, ShapeableImageView shapeableImageView);
}
