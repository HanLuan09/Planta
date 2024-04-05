package vn.edu.ptit.planta.model.care;

import com.google.gson.annotations.JsonAdapter;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

import vn.edu.ptit.planta.config.SqlDateTypeAdapter;
import vn.edu.ptit.planta.config.SqlTimeTypeAdapter;

public class CareSchedule implements Serializable {
    private int myPlantId;
    private String myPlantName;
    private String image;
    @JsonAdapter(SqlDateTypeAdapter.class)
    private Date startDate;
    @JsonAdapter(SqlDateTypeAdapter.class)
    private Date endDate;
    @JsonAdapter(SqlTimeTypeAdapter.class)
    private Time time;
    private int frequency;

    public CareSchedule(int myPlantId, String myPlantName) {
        this.myPlantId = myPlantId;
        this.myPlantName = myPlantName;
    }

    public CareSchedule(int myPlantId, String myPlantName, String image, Date startDate, Date endDate, Time time, int frequency) {
        this.myPlantId = myPlantId;
        this.myPlantName = myPlantName;
        this.image = image;
        this.startDate = startDate;
        this.endDate = endDate;
        this.time = time;
        this.frequency = frequency;
    }

    public int getMyPlantId() {
        return myPlantId;
    }

    public String getMyPlantName() {
        return myPlantName;
    }

    public String getImage() {
        return image;
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

    public void setMyPlantId(int myPlantId) {
        this.myPlantId = myPlantId;
    }

    public void setMyPlantName(String myPlantName) {
        this.myPlantName = myPlantName;
    }

    public void setImage(String image) {
        this.image = image;
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
