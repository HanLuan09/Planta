package vn.edu.ptit.planta.model;

public class DataStatus {
    private boolean status;
    private String message;

    public DataStatus(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
