package vn.edu.ptit.planta.ui.test;

public class Schedule {
    private String name;
    private String time;
    private int frequency; // Số ngày lặp lại

    public Schedule(String name, String time, int frequency) {
        this.name = name;
        this.time = time;
        this.frequency = frequency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}