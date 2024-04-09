package vn.edu.ptit.planta.model.care;

import java.io.Serializable;
import java.util.List;

public class CareScheduleResponse implements Serializable {
    private String name;
    List<CareSchedule> careSchedules;

    public CareScheduleResponse() {
    }

    public CareScheduleResponse(String name, List<CareSchedule> careSchedules) {
        this.name = name;
        this.careSchedules = careSchedules;
    }

    public String getName() {
        return name;
    }

    public List<CareSchedule> getCareSchedules() {
        return careSchedules;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCareSchedules(List<CareSchedule> careSchedules) {
        this.careSchedules = careSchedules;
    }
}
