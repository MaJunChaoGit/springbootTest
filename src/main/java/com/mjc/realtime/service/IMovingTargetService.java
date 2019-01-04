package com.mjc.realtime.service;

import com.mjc.realtime.entity.Heatmap;
import com.mjc.realtime.entity.LifeCircle;
import com.mjc.realtime.entity.MovingTarget;
import com.mjc.realtime.vo.MovingTargetDataVo;

import java.text.ParseException;
import java.util.List;

public interface IMovingTargetService {
    public LifeCircle getTimeInterval() throws ParseException;
    public int saveMovingTarget(MovingTarget movingTarget);
    public int saveHeatmap();
    public List<Heatmap> getHeatmap();
}
