package vn.edu.ptit.planta.model;

public class AttributeOfMyPlant {

    private String title;
    private String content;

    public AttributeOfMyPlant(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
