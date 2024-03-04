package vn.edu.ptit.planta.model;

import java.io.Serializable;

public class Schedule implements Serializable {

    private int id;
    private String name;
    private String desc;

    public Schedule(int id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }
}
