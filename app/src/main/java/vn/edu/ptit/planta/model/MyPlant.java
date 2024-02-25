package vn.edu.ptit.planta.model;

import java.util.Date;

public class MyPlant {
    private Long id;
    private String name;
    private String image;
    private Date createDate;

    public MyPlant() {
    }

    public MyPlant(Long id, String name, String image, Date createDate) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public Date getCreateDate() {
        return createDate;
    }
}
