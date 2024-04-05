package vn.edu.ptit.planta.model.care;

import com.google.gson.annotations.JsonAdapter;

import java.sql.Date;
import java.sql.Time;

import vn.edu.ptit.planta.config.SqlDateTypeAdapter;
import vn.edu.ptit.planta.config.SqlTimeTypeAdapter;

public class CareCalendar {
    private int myScheduleId;
    private String name;
    @JsonAdapter(SqlDateTypeAdapter.class)
    private Date startDate;
    @JsonAdapter(SqlDateTypeAdapter.class)
    private Date endDate;
    @JsonAdapter(SqlTimeTypeAdapter.class)
    private Time time;
    private int frequency;

    public int getMyScheduleId() {
        return myScheduleId;
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

    public void setMyScheduleId(int myScheduleId) {
        this.myScheduleId = myScheduleId;
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
