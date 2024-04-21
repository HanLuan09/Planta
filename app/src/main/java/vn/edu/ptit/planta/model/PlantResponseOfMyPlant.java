package vn.edu.ptit.planta.model;

import java.io.Serializable;

public class PlantResponseOfMyPlant implements Serializable {
    private int id;
    private String name;
    private String typePlant;
    private String mainImage;
    private String secondaryImage;
    private String typeSoil;
    private String matureSize;
    private String matureTime;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypePlant() {
        return typePlant;
    }

    public void setTypePlant(String typePlant) {
        this.typePlant = typePlant;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public String getSecondaryImage() {
        return secondaryImage;
    }

    public void setSecondaryImage(String secondaryImage) {
        this.secondaryImage = secondaryImage;
    }

    public String getTypeSoil() {
        return typeSoil;
    }

    public void setTypeSoil(String typeSoil) {
        this.typeSoil = typeSoil;
    }

    public String getMatureSize() {
        return matureSize;
    }

    public void setMatureSize(String matureSize) {
        this.matureSize = matureSize;
    }

    public String getMatureTime() {
        return matureTime;
    }

    public void setMatureTime(String matureTime) {
        this.matureTime = matureTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "PlantDetailOfMyPlant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", typePlant='" + typePlant + '\'' +
                ", mainImage='" + mainImage + '\'' +
                ", secondaryImage='" + secondaryImage + '\'' +
                ", typeSoil='" + typeSoil + '\'' +
                ", matureSize='" + matureSize + '\'' +
                ", matureTime='" + matureTime + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
