package vn.edu.ptit.planta.model;

import java.sql.Date;

public class Schedule {
    private int id;
    private String name;
    private Date startTime;
    private int duration;
    private String description;

    public Schedule() {
    }

    public Schedule(int id, String name, Date startTime, int duration, String description) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.duration = duration;
        this.description = description;
    }

}
