package com.main.SpanCallerViewer.model;

public class UserLogin {

    private String number;

    private String password;

    public UserLogin() {
    }

    public UserLogin(String number, String password) {
        this.number = number;
        this.password = password;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
