package com.mjc.realtime.entity;

import java.io.Serializable;
import java.util.List;

public class MovingTarget implements Serializable{
    private static final long serialVersionUID = 380324744056871766L;
    private String id;
    private String startTime;
    private String endTime;
    private Options options;
    private List<TimePosition> timePositions;


    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<TimePosition> getTimePositions() {
        return timePositions;
    }

    public void setTimePositions(List<TimePosition> timePositions) {
        this.timePositions = timePositions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions(Options options) {
        this.options = options;
    }
}
