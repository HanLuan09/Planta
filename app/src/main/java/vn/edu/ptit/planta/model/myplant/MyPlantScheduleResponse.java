package vn.edu.ptit.planta.model.myplant;

import java.util.List;

import vn.edu.ptit.planta.model.myschedule.MySchedule;

public class MyPlantScheduleResponse {
    private int id;
    private String name;
    private String image;
    List<MySchedule> mySchedules;

    public MyPlantScheduleResponse() {
    }

    public MyPlantScheduleResponse(int id, String name, String image, List<MySchedule> mySchedules) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.mySchedules = mySchedules;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public List<MySchedule> getMySchedules() {
        return mySchedules;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setMySchedules(List<MySchedule> mySchedules) {
        this.mySchedules = mySchedules;
    }
}
