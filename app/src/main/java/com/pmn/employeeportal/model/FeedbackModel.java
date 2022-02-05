package com.pmn.employeeportal.model;

public class FeedbackModel {
    public String reason;
    public String comment;
    public String userName;
    public String userEmail;
    public String userId;


    public FeedbackModel(String reason, String comment, String userName, String userEmail, String userId) {
        this.reason = reason;
        this.comment = comment;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userId = userId;
    }

    public FeedbackModel() {
    }
}
