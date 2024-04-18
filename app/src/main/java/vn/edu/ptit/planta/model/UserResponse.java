package vn.edu.ptit.planta.model;

import java.io.Serializable;

public class UserResponse implements Serializable {
    private int id;
    private String name;
    private String phone;
    private String address;
    private String email;
    private String token;

    public UserResponse() {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.token = token;
    }

    public UserResponse(int id, String name, String phone, String address, String email, String token) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
