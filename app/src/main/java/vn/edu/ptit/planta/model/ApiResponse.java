package vn.edu.ptit.planta.model;

public class ApiResponse<T> {

    private boolean success;
    private int code;
    private String message;
    private T result;

    public ApiResponse() {
    }

    public ApiResponse(boolean success, int code, String message, T result) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getResult() {
        return result;
    }
}
