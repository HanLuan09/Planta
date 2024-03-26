package vn.edu.ptit.planta.model.myschedule;

import java.sql.Date;
import java.sql.Time;

public class MyScheduleRequest extends MySchedule {

    private int idMyPlant;

    public MyScheduleRequest() {
    }

    public MyScheduleRequest(int id, String name, Date startDate, Date endDate, Time time, int frequency) {
        super(id, name, startDate, endDate, time, frequency);
    }
}
