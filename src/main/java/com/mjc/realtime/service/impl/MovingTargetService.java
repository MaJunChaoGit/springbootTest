package com.mjc.realtime.service.impl;

import com.mjc.realtime.entity.*;
import com.mjc.realtime.dao.mapper.IMovingTarget;
import com.mjc.realtime.service.IMovingTargetService;
import com.mjc.realtime.utils.GeoHash;
import com.mjc.realtime.utils.Timer;
import com.mjc.realtime.vo.MovingTargetDataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class MovingTargetService implements IMovingTargetService {

    @Autowired
    private IMovingTarget movingTargetDAO;

    @Autowired
    private RedisTemplate redisTemplate;
    private final String lifeCircleKey = "lifeCircle";
    private final String movingTargetsKey = "movingTaget";
    public static String startTime;
    public static String endTime;
    public static List<MovingTarget> movingTargets = new ArrayList<MovingTarget>();


    private int compareData(String date1, String date2) throws ParseException {
        if (date1.equals("") || date2.equals("")) return 0;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date startTimeOrigin = df.parse(date1);
        Date startTimeNew = df.parse(date2);
        if (startTimeOrigin.getTime() > startTimeNew.getTime()) {
            return 1;
        }
        return -1;
    }
    @Timer
    private LifeCircle getLifeCircleForRedis() {

        ValueOperations<String, LifeCircle> operationLifeCircle = redisTemplate.opsForValue();
        if (redisTemplate.hasKey(lifeCircleKey)) {
            LifeCircle lifeCircle = operationLifeCircle.get(lifeCircleKey);
            startTime = lifeCircle.getOverallStarttime();
            endTime = lifeCircle.getOverallEndtime();
            return lifeCircle;
        }
        return null;
    }
    @Timer
    private List<MovingTarget> getMovingTarget() {
        ValueOperations<String, List<MovingTarget>> operationMovingTargets = redisTemplate.opsForValue();
        List<MovingTarget> movingTargets;
        if (redisTemplate.hasKey(movingTargetsKey)) {
            // 如果redis有就从redis取出
            movingTargets = operationMovingTargets.get(movingTargetsKey);
        } else {
            // 获取数据库中所有的动目标信息
            movingTargets = movingTargetDAO.getiInfomation();
            // 存入redis
            operationMovingTargets.set(movingTargetsKey, movingTargets, 50, TimeUnit.MINUTES);
        }
        return movingTargets;
    }

    @Override
    @Timer
    public LifeCircle getTimeInterval() {
        // 获取动目标数据
        movingTargets = getMovingTarget();
        // 如果redis里面没有数据的话就需要自己判断了
        LifeCircle lifeCircle = getLifeCircleForRedis();
        if (lifeCircle != null) return lifeCircle;

        // 设置起始和结束时间
        lifeCircle = new LifeCircle();
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

        lifeCircle.setOverallStarttime(overallStarttime);
        lifeCircle.setOverallEndtime(overallEndtime);
        lifeCircle.setMultiplier(1);
        startTime = overallStarttime;
        endTime = overallEndtime;
        // 存入redis
        ValueOperations<String, LifeCircle> operationLifeCircle = redisTemplate.opsForValue();
        operationLifeCircle.set(lifeCircleKey, lifeCircle, 50, TimeUnit.MINUTES);
        return lifeCircle;
    }

    @Override
    public int saveMovingTarget(MovingTarget movingTarget) {
        int positionsCount = 0;
        movingTargetDAO.saveInfomation(movingTarget);
        String pid = movingTarget.getId();

        for (TimePosition timePosition : movingTarget.getTimePositions()) {
            timePosition.setPid(pid);
            GeoHash g = new GeoHash(timePosition.getLat(), timePosition.getLon());
            g.sethashLength(12);
            String geoHash = g.getGeoHashBase32();
            timePosition.setGeohash(geoHash);
            positionsCount += movingTargetDAO.saveTimePosition(timePosition);
        }

        if (positionsCount > 0) {
            redisTemplate.delete(movingTargetsKey);
        }
        return positionsCount;
    }

    @Override
    public int saveHeatmap() {
        List<TimePosition> timePositions = movingTargetDAO.getTimePosition();
        HashMap<String, Integer> geohashMap = new HashMap<String, Integer>();
        int sum = 0;
        for (TimePosition timePosition : timePositions) {
            String hash = timePosition.getGeohash().substring(0, 6);
            if (geohashMap.containsKey(hash)) {
                Integer count = geohashMap.get(hash);
                count++;
                geohashMap.put(hash, count);
            } else {
                geohashMap.put(hash, 1);
            }
        }
        for (Map.Entry entry : geohashMap.entrySet()) {
            Heatmap heat = new Heatmap();
            heat.setGeohash((String) entry.getKey());
            heat.setValue((int) entry.getValue());
            GeoHash g = new GeoHash();
            LocationBean bean = g.getLocation(heat.getGeohash());
            heat.setLon(bean.getLng());
            heat.setLat(bean.getLat());
            movingTargetDAO.UpdateOrInsertHeatmap(heat);
            sum++;
        }
        return sum;
    }

    public List<Heatmap> getHeatmap() {
        return movingTargetDAO.getHeatmap();
    }
}
