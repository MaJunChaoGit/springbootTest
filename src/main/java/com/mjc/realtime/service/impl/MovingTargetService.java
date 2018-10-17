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
        // 获取数据库中所有的动目标信息
        List<MovingTarget> movingTargets = movingTargetDAO.getiInfomation();
        // 新建一个动目标Vo类
        MovingTargetDataVo movingTargetDataVo = new MovingTargetDataVo();
        // 获取第一个动目标的起始时间
        String overallStarttime = movingTargets.get(0).getStartTime();
        // 获取第一个动目标的结束时间
        String overallEndtime = movingTargets.get(0).getEndTime();
        // 遍历动目标数组, 找出最先的起始时间和最后的结束时间
        for (MovingTarget movingTarget : movingTargets) {
            try {
                // 比较前后两个动目标的时间
                int startResult = compareData(overallStarttime, movingTarget.getStartTime());
                int endResult = compareData(overallEndtime, movingTarget.getEndTime());
                // 当startResult = 1的时候覆盖起始时间
                if (startResult == 1) {
                    overallStarttime = movingTarget.getStartTime();
                }
                // 当startResult = 0的时候覆盖结束时间
                if (endResult == -1) {
                    overallEndtime = movingTarget.getEndTime();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        // 设置起始和结束时间
        movingTargetDataVo.setOverallStarttime(overallStarttime);
        movingTargetDataVo.setOverallEndtime(overallEndtime);
        // 设置动目标数据给Vo类
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
