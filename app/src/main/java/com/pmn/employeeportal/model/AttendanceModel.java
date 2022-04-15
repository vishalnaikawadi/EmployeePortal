package com.pmn.employeeportal.model;

import java.util.List;

public class AttendanceModel {
    private List<String> present = null;
    private List<String> absent = null;

    public List<String> getPresent() {
        return present;
    }

    public void setPresent(List<String> present) {
        this.present = present;
    }

    public List<String> getAbsent() {
        return absent;
    }

    public void setAbsent(List<String> absent) {
        this.absent = absent;
    }
}
