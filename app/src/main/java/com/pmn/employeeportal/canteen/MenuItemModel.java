package com.pmn.employeeportal.canteen;

public class MenuItemModel implements MenuInterface {
    private String itemName;

    public MenuItemModel(String itemName) {
        this.itemName = itemName;
    }

    public MenuItemModel() {
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
