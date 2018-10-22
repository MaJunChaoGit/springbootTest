package com.mjc.realtime.entity;

import java.io.Serializable;

public class TimePosition implements Serializable {
    private static final long serialVersionUID = 2945383354517207601L;
    private int id;
    private String time;
    private Double lon;
    private Double lat;
    private Double height;
    private String pid;
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

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
