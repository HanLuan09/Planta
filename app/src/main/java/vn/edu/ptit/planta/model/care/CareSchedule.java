package vn.edu.ptit.planta.model.care;

import java.io.Serializable;

public class CareSchedule implements Serializable {
    private int id;
    private String name;

    private String image;
    private String  time;

    public CareSchedule() {
    }

    public CareSchedule(int id, String name, String time) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.image = "https://www.cleanipedia.com/images/5iwkm8ckyw6v/4ZwMFO8WOz3sQGFl3RHNLW/41535593e4c3881b21e59666594da804/Y2FjaC1jaGFtLXNvYy1jYXktaG9hLWhvbmcuamZpZg/1200w/c%C3%A1ch-ch%C4%83m-s%C3%B3c-c%C3%A2y-hoa-h%E1%BB%93ng-chu%E1%BA%A9n%2C-kh%E1%BB%8Fe-th%C3%A2n%2C-d%C3%A0y-c%C3%A1nh.jpg";
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getImage() {
        return image;
    }
}
