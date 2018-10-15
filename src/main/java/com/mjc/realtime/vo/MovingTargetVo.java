package com.mjc.realtime.vo;

import com.mjc.realtime.entity.MovingTarget;
import com.mjc.realtime.entity.TimePosition;

import java.util.List;

public class MovingTargetVo extends MovingTarget {
    private List<TimePosition> timePositionList;

    public List<TimePosition> getTimePositionList() {
        return timePositionList;
    }

    public void setTimePositionList(List<TimePosition> timePositionList) {
        this.timePositionList = timePositionList;
    }
}
