package com.example.isibpmn.data.model;

public class LoginResponse {
    private  int status;
    private  String message;
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LoginResponse(String message) {
        this.message = message;
    }
}
