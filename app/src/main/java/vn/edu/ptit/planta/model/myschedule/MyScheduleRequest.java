package vn.edu.ptit.planta.model.myschedule;

import com.google.gson.annotations.JsonAdapter;

import java.sql.Date;
import java.sql.Time;

import vn.edu.ptit.planta.config.SqlDateTypeAdapter;
import vn.edu.ptit.planta.config.SqlTimeTypeAdapter;


public class MyScheduleRequest {
    private String name;
    @JsonAdapter(SqlDateTypeAdapter.class)
    private Date startDate;
    @JsonAdapter(SqlDateTypeAdapter.class)
    private Date endDate;
    @JsonAdapter(SqlTimeTypeAdapter.class)
    private Time time;
    private int frequency;
    private int myPlantId;

    public MyScheduleRequest() {
    }

    public MyScheduleRequest(String name, Date startDate, Date endDate, Time time, int frequency, int myPlantId) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.time = time;
        this.frequency = frequency;
        this.myPlantId = myPlantId;
    }
    public MyScheduleRequest(String name, Date startDate, Date endDate, Time time, int frequency) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.time = time;
        this.frequency = frequency;
    }
}
