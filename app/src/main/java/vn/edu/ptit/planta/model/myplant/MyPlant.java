package vn.edu.ptit.planta.model.myplant;


import java.io.Serializable;

import vn.edu.ptit.planta.model.PlantResponseOfMyPlant;

public class MyPlant implements Serializable {
    private int id;
    private String name;
    private String image;
    private String kindOfLight;
    private String grownDate;
    private PlantResponseOfMyPlant plantResponseOfMyPlant;

    public MyPlant() {
    }

    public MyPlant(int id, String name, String image, String kindOfLight, String grownDate, PlantResponseOfMyPlant plantResponseOfMyPlant) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.kindOfLight = kindOfLight;
        this.grownDate = grownDate;
        this.plantResponseOfMyPlant = plantResponseOfMyPlant;
    }

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKindOfLight() {
        return kindOfLight;
    }

    public void setKindOfLight(String kindOfLight) {
        this.kindOfLight = kindOfLight;
    }

    public String getGrownDate() {
        return grownDate;
    }

    public void setGrownDate(String grownDate) {
        this.grownDate = grownDate;
    }

    public PlantResponseOfMyPlant getPlantDetailOfMyPlant() {
        return plantResponseOfMyPlant;
    }

    public void setPlantDetailOfMyPlant(PlantResponseOfMyPlant plantResponseOfMyPlant) {
        this.plantResponseOfMyPlant = plantResponseOfMyPlant;
    }
}
