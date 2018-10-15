package com.mjc.realtime.service;

import com.mjc.realtime.entity.MovingTarget;
import com.mjc.realtime.vo.MovingTargetVo;

import java.util.List;

public interface IMovingTargetService {
    public List<MovingTarget> getMovingTargetInfo();

    public int saveMovingTarget(MovingTarget movingTarget);
}
