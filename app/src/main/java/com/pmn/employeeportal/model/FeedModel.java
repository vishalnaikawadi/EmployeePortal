
package com.pmn.employeeportal.model;

import java.util.List;

public class FeedModel {

    private List<Notice> notices = null;
    private List<Birthday> birthdays = null;
    private List<NewJoinee> newJoinees = null;
    private List<Event> events = null;

    public List<Notice> getNotices() {
        return notices;
    }

    public void setNotices(List<Notice> notices) {
        this.notices = notices;
    }

    public List<Birthday> getBirthdays() {
        return birthdays;
    }

    public void setBirthdays(List<Birthday> birthdays) {
        this.birthdays = birthdays;
    }

    public List<NewJoinee> getNewJoinees() {
        return newJoinees;
    }

    public void setNewJoinees(List<NewJoinee> newJoinees) {
        this.newJoinees = newJoinees;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

}
