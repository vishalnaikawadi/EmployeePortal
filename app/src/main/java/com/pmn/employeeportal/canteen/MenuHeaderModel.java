package com.pmn.employeeportal.canteen;

public class MenuHeaderModel implements MenuInterface {
    private String header;

    public MenuHeaderModel(String header) {
        this.header = header;
    }

    public MenuHeaderModel() {
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}
