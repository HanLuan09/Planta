package vn.edu.ptit.planta.model.care;

import java.io.Serializable;

public class CareSchedule implements Serializable {
    private int id;
    private String name;
    private String  time;

    public CareSchedule() {
    }

    public CareSchedule(int id, String name, String time) {
        this.id = id;
        this.name = name;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }
}
