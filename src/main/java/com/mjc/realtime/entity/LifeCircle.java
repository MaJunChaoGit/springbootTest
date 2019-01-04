package com.mjc.realtime.entity;

import java.io.Serializable;

public class LifeCircle implements Serializable {
    private static final long serialVersionUID = 279564815818775707L;
    private String overallStarttime;
    private String overallEndtime;
    private int multiplier;

    public String getOverallStarttime() {
        return overallStarttime;
    }

    public void setOverallStarttime(String overallStarttime) {
        this.overallStarttime = overallStarttime;
    }

    public String getOverallEndtime() {
        return overallEndtime;
    }

    public void setOverallEndtime(String overallEndtime) {
        this.overallEndtime = overallEndtime;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }
}
