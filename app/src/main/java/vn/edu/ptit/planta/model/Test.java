package vn.edu.ptit.planta.model;

import java.io.Serializable;

public class Test implements Serializable {
    private String name;
    private String desc;

    public Test(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }
}
