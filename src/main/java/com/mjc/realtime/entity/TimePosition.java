package com.mjc.realtime.entity;

public class TimePosition {
    private int id;
    private String time;
    private String lon;
    private String lat;
    private String height;
    private int pid;
    private MovingTarget movingTarget;

    public MovingTarget getMovingTarget() {
        return movingTarget;
    }

    public void setMovingTarget(MovingTarget movingTarget) {
        this.movingTarget = movingTarget;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }
}
