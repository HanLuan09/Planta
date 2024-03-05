package vn.edu.ptit.planta.model.care;

public class ScheduleCare {

    private String name;
    private int img;

    public ScheduleCare(String name, int img) {
        this.name = name;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public int getImg() {
        return img;
    }
}
