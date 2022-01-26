package com.pmn.employeeportal.model;

public class User {

    public String userName;
    public String userEmail;
    public String userPhone;
    public String userProfile;

    public User(String userName, String userEmail, String userPhone, String userProfile) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.userProfile = userProfile;
    }

    public User() {
    }
}
