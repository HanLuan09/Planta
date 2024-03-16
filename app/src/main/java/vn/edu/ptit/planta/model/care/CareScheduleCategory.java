package vn.edu.ptit.planta.model.care;

import java.io.Serializable;
import java.util.List;

public class CareScheduleCategory implements Serializable {
    private String exercise;
    private List<CareSchedule> careSchedules;

    public CareScheduleCategory() {
    }

    public CareScheduleCategory(String exercise, List<CareSchedule> careSchedules) {
        this.exercise = exercise;
        this.careSchedules = careSchedules;
    }

    public String getExercise() {
        return exercise;
    }

    public List<CareSchedule> getCareSchedules() {
        return careSchedules;
    }
}
