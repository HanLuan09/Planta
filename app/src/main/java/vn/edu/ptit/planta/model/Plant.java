package vn.edu.ptit.planta.model;

import java.io.Serializable;

public class Plant implements Serializable {
    private int id;
    private String name;
    private String image;

    public Plant(int id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}
