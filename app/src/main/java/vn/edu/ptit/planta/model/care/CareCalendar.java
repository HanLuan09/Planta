package vn.edu.ptit.planta.model.care;

import java.util.List;

public class CareCalendar {
    private String Date;
    private List<CareCalendarSchedule> careCalendarSchedules;

    public CareCalendar(String date, List<CareCalendarSchedule> careCalendarSchedules) {
        Date = date;
        this.careCalendarSchedules = careCalendarSchedules;
    }

    public String getDate() {
        return Date;
    }

    public List<CareCalendarSchedule> getCareCalendarSchedules() {
        return careCalendarSchedules;
    }
}
