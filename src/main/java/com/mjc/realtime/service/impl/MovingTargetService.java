package com.mjc.realtime.service.impl;

import com.mjc.realtime.entity.MovingTarget;
import com.mjc.realtime.entity.TimePosition;
import com.mjc.realtime.mapper.IMovingTarget;
import com.mjc.realtime.service.IMovingTargetService;
import com.mjc.realtime.vo.MovingTargetDataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@Service
public class MovingTargetService implements IMovingTargetService{

    @Autowired
    private IMovingTarget movingTargetDAO;

    public int compareData(String date1, String date2) throws ParseException {
        if (date1.equals("") || date2.equals("")) return 0;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date startTimeOrigin = df.parse(date1);
        Date startTimeNew = df.parse(date2);
        if (startTimeOrigin.getTime() > startTimeNew.getTime()) {
           return 1;
        }
        return -1;
    }
    @Override
    public MovingTargetDataVo getMovingTargetInfo(){
        List<MovingTarget> movingTargets = movingTargetDAO.getiInfomation();
        MovingTargetDataVo movingTargetDataVo = new MovingTargetDataVo();
        String overallStarttime = movingTargets.get(0).getStartTime();
        String overallEndtime = movingTargets.get(0).getEndTime();
        for (MovingTarget movingTarget : movingTargets) {

            try {
                int startResult = compareData(overallStarttime, movingTarget.getStartTime());
                int endResult = compareData(overallEndtime, movingTarget.getEndTime());

                if (startResult == 1) {
                    overallStarttime = movingTarget.getStartTime();
                }

                if (endResult == -1) {
                    overallEndtime = movingTarget.getEndTime();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        movingTargetDataVo.setOverallStarttime(overallStarttime);
        movingTargetDataVo.setOverallEndtime(overallEndtime);
        movingTargetDataVo.setData(movingTargets);
        return movingTargetDataVo;
    }

    @Override
    public int saveMovingTarget(MovingTarget movingTarget) {

        int positionsCount = 0;
        movingTargetDAO.saveInfomation(movingTarget);
        String pid = movingTarget.getId();

        for (TimePosition timePosition : movingTarget.getTimePositions()) {
            timePosition.setPid(pid);
            positionsCount = movingTargetDAO.saveTimePosition(timePosition);
        }
        return positionsCount;
    }
}
