package com.example.foodapp.models;

public class UserModels {

    int id;
    String fullName;
    String phoneNum;
    String Password;

    public UserModels() {    }

    public UserModels(int id, String fullName, String phoneNum, String password) {
        this.id = id;
        this.fullName = fullName;
        this.phoneNum = phoneNum;
        this.Password = password;
    }

    public UserModels(String fullName, String phoneNum, String password) {
        this.fullName = fullName;
        this.phoneNum = phoneNum;
        this.Password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
