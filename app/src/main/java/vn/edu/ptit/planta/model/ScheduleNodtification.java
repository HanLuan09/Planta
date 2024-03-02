package vn.edu.ptit.planta.model;

import java.io.Serializable;

public class ScheduleNodtification implements Serializable {

    private String name;
    private String startDate;
    private String endDate;
    private String time;
    private int frequency;

    public ScheduleNodtification(String name, String startDate, String endDate, String time, int frequency) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.time = time;
        this.frequency = frequency;
    }

    public String getName() {
        return name;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getTime() {
        return time;
    }

    public int getFrequency() {
        return frequency;
    }
}
