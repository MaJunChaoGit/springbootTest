package com.mjc.realtime.entity;

import java.util.List;

public class MovingTarget {
    private int id;
    private int type;
    private String phone;
    private String ascription;
    private List<TimePosition> timePositions;

    public List<TimePosition> getTimePositions() {
        return timePositions;
    }

    public void setTimePositions(List<TimePosition> timePositions) {
        this.timePositions = timePositions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAscription() {
        return ascription;
    }

    public void setAscription(String ascription) {
        this.ascription = ascription;
    }
}
