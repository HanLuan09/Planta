package vn.edu.ptit.planta.ui.note;

import java.io.Serializable;
import java.util.Date;

public class Note implements Serializable {
    private int id;
    private String description;
    private int flowers;
    private int fruits;
    private int height;
    private String date;
    private String image;

    private Integer idmyplant;

    public Note() {
        super();
    }

    public Note(int id, String description, int flowers, int fruits, int height, String date, String image, Integer idmyplant) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.flowers = flowers;
        this.fruits = fruits;
        this.height = height;
        this.image = image;
        this.idmyplant = idmyplant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getFlowers() {
        return flowers;
    }

    public void setFlowers(int flowers) {
        this.flowers = flowers;
    }

    public int getFruits() {
        return fruits;
    }

    public void setFruits(int fruits) {
        this.fruits = fruits;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getIdmyplant() {
        return idmyplant;
    }

    public void setIdmyplant(Integer idmyplant) {
        this.idmyplant = idmyplant;
    }
}
