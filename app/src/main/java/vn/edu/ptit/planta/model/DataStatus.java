package vn.edu.ptit.planta.model;

public class DataStatus {
    private boolean status;

    private  boolean loader;
    private String message;

    public DataStatus(boolean status, boolean loader, String message) {
        this.status = status;
        this.loader = loader;
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public boolean isLoader() {
        return loader;
    }

    public String getMessage() {
        return message;
    }
}
