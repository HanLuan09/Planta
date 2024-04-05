package vn.edu.ptit.planta.model.plant;

import java.io.Serializable;

public class Plant implements Serializable {
    private int id;
    private String name;
    private String typePlant;
    private String mainImage;

    public Plant() {
        super();
    }
    public Plant(int id, String name, String typePlant, String mainImage) {
        super();
        this.id = id;
        this.name = name;
        this.typePlant = typePlant;
        this.mainImage = mainImage;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getTypePlant() {
        return typePlant;
    }
    public String getMainImage() {
        return mainImage;
    }
}
