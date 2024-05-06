package vn.edu.ptit.planta.ui.note;

import java.io.Serializable;

public class SampleResponse implements Serializable {
    private int id;

    private String flowers;

    private String fruits;

    private String height;

    public SampleResponse() {
        super();
    }

    public SampleResponse(int id, String flowers, String fruits, String height) {
        this.id = id;
        this.flowers = flowers;
        this.fruits = fruits;
        this.height = height;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
