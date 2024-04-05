package vn.edu.ptit.planta.model.myschedule;

import com.google.gson.annotations.JsonAdapter;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

import vn.edu.ptit.planta.config.SqlDateTypeAdapter;
import vn.edu.ptit.planta.config.SqlTimeTypeAdapter;

public class MySchedule implements Serializable {

    private int id;
    private String name;
    @JsonAdapter(SqlDateTypeAdapter.class)
    private Date startDate;
    @JsonAdapter(SqlDateTypeAdapter.class)
    private Date endDate;
    @JsonAdapter(SqlTimeTypeAdapter.class)
    private Time time;

    private int frequency;

    public MySchedule() {
    }

    public MySchedule(int id, String name, Date startDate, Date endDate, Time time, int frequency) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.time = time;
        this.frequency = frequency;
    }
    public MySchedule(String name, Date startDate, Date endDate, Time time, int frequency) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.time = time;
        this.frequency = frequency;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Time getTime() {
        return time;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
