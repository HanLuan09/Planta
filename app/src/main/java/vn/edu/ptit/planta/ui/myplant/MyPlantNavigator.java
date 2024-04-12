package vn.edu.ptit.planta.ui.myplant;

import android.graphics.Bitmap;

import com.google.android.material.imageview.ShapeableImageView;

import vn.edu.ptit.planta.model.myplant.MyPlant;

public interface MyPlantNavigator {

    public void handleError(Throwable throwable);

    public void handleSearchMyPlant();

    public void handleMyPlantDetail(MyPlant myPlant);
    public void glideImage(String image, ShapeableImageView shapeableImageView);
}
