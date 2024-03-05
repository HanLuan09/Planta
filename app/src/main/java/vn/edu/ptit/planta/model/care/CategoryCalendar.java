package vn.edu.ptit.planta.model.care;

import java.util.List;

public class CategoryCalendar {

    private int year;
    private int month;
    private int day;

    private List<ScheduleCare> scheduleCares;

    public CategoryCalendar(int year, int month, int day, List<ScheduleCare> scheduleCares) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.scheduleCares = scheduleCares;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public List<ScheduleCare> getScheduleCares() {
        return scheduleCares;
    }
}
