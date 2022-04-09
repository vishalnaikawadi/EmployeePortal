package com.pmn.employeeportal.model;

public class LeaveModel {
    public String id;
    public String fromDate;
    public String toDate;
    public String reason;
    public String status;
    public String remark;
    public String userEmail;

    public LeaveModel(String id, String fromDate, String toDate, String reason) {
        this.id = id;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.reason = reason;
    }

    public LeaveModel(String id, String fromDate, String toDate, String reason, String status, String remark, String userEmail) {
        this.id = id;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.reason = reason;
        this.status = status;
        this.remark = remark;
        this.userEmail = userEmail;
    }

    public LeaveModel() {
    }
}
