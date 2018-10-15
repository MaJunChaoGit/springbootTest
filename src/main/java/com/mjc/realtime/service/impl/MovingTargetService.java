package com.mjc.realtime.service.impl;

import com.mjc.realtime.entity.MovingTarget;
import com.mjc.realtime.entity.TimePosition;
import com.mjc.realtime.mapper.IMovingTarget;
import com.mjc.realtime.service.IMovingTargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MovingTargetService implements IMovingTargetService{

    @Autowired
    private IMovingTarget movingTargetDAO;

    @Override
    public List<MovingTarget> getMovingTargetInfo() {
        return movingTargetDAO.getiInfomation();
    }

    @Override
    public int saveMovingTarget(MovingTarget movingTarget) {
        int positionsCount = 0;
        movingTargetDAO.saveInfomation(movingTarget);
        int pid = movingTarget.getId();

        for (TimePosition timePosition : movingTarget.getTimePositions()) {
            timePosition.setPid(pid);
            positionsCount = movingTargetDAO.saveTimePosition(timePosition);
        }
        return positionsCount;
    }
}
