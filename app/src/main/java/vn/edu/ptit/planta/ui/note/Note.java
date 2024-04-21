package vn.edu.ptit.planta.ui.note;

import java.io.Serializable;

public class Note implements Serializable {
    private String id;
    private String description;
    private String date;
    private String flowers;
    private String fruits;
    private String height;

    private String image;

    public Note() {
    }

    public Note(String id, String description, String date, String flowers, String fruits, String height, String image) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.flowers = flowers;
        this.fruits = fruits;
        this.height = height;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getFlowers() {
        return flowers;
    }

    public void setFlowers(String flowers) {
        this.flowers = flowers;
    }

    public String getFruits() {
        return fruits;
    }

    public void setFruits(String fruits) {
        this.fruits = fruits;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
