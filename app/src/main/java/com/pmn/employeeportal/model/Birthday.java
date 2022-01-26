
package com.pmn.employeeportal.model;

import com.pmn.employeeportal.utils.FeedMarker;

public class Birthday implements FeedMarker {

    private String name;
    private String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
