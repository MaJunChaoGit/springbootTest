package com.mjc.realtime.service;

import com.mjc.realtime.entity.Heatmap;
import com.mjc.realtime.entity.MovingTarget;
import com.mjc.realtime.vo.MovingTargetDataVo;

import java.text.ParseException;
import java.util.List;

public interface IMovingTargetService {
    public MovingTargetDataVo getMovingTargetInfo() throws ParseException;
    public int saveMovingTarget(MovingTarget movingTarget);
    public void saveHeatmap();
    public List<Heatmap> getHeatmap();
}
