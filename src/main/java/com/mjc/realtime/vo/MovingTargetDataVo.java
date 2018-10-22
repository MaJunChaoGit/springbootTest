package com.mjc.realtime.vo;

import com.mjc.realtime.entity.MovingTarget;


import java.io.Serializable;
import java.util.List;

public class MovingTargetDataVo implements Serializable{

    private static final long serialVersionUID = -4124713900999435110L;
    private String overallStarttime;
    private String overallEndtime;
    private int multiplier = 1;
    private List<MovingTarget> data;

    public List<MovingTarget> getData() {
        return data;
    }

    public void setData(List<MovingTarget> data) {
        this.data = data;
    }

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
