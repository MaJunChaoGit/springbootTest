package com.mjc.realtime.entity;

public class Options {
    private String id;
    private String type;
    private String ascriptions;
    private String position;
    private String phone;
    private String time = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAscriptions() {
        return ascriptions;
    }

    public void setAscriptions(String ascriptions) {
        this.ascriptions = ascriptions;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
