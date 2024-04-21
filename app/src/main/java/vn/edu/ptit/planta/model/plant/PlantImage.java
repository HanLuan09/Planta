package vn.edu.ptit.planta.model.plant;

import java.io.Serializable;

public class PlantImage implements Serializable {
    private String image;

    public PlantImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }
}
