package com.mjc.realtime.mapper;

import com.mjc.realtime.entity.Heatmap;
import com.mjc.realtime.entity.MovingTarget;
import com.mjc.realtime.entity.TimePosition;

import java.util.List;

public interface IMovingTarget {
    // 获取所有动目标的信息
    public List<MovingTarget> getiInfomation();
    // 根据动目标的id查找当前的位置信息
    public List<TimePosition> getTimePosition(String id);
    // 查询所有动目标位置信息
    public List<TimePosition> getTimePosition();
    // 存储动目标数据的接口
    public int saveInfomation(MovingTarget movingTarget);
    // 存储动目标详细信息接口
    public int saveTimePosition(TimePosition timePosition);
    // 存储热力图接口信息
    public int UpdateOrInsertHeatmap(Heatmap heatmap);

    public List<Heatmap> getHeatmap();
}
