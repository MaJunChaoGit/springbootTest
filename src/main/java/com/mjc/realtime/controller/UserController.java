package com.mjc.realtime.controller;

import com.mjc.realtime.entity.Heatmap;
import com.mjc.realtime.entity.LifeCircle;
import com.mjc.realtime.entity.MovingTarget;
import com.mjc.realtime.service.IMovingTargetService;
import com.mjc.realtime.vo.MovingTargetDataVo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@CrossOrigin(origins = "http://mozhengying.com", maxAge = 3600)
@RestController
public class UserController {

    @Autowired
    private IMovingTargetService movingTargetService;
    private static Logger log  = Logger.getLogger(UserController.class);

    /**
     * 获取动目标信息开始结束时间
     * @return
     */
    @RequestMapping(value = "/lifeCircle", method = {RequestMethod.GET})
    public LifeCircle movingTargets(){
        try {
            LifeCircle lifeCircle = movingTargetService.getTimeInterval();
            if (lifeCircle != null) log.info("获取动目标信息开始结束时间成功");
            return lifeCircle;
        } catch (ParseException e) {
            e.printStackTrace();
            log.error("获取动目标信息开始结束时间失败");
        }
        return null;
    }

    /**
     * 保存动目标信息接口
     * @param movingTarget
     * @return
     */
    @RequestMapping(value = "/saveMovingTarget", method = {RequestMethod.POST})
    public int saveMovingTarget(@RequestBody MovingTarget movingTarget) {
        int count = movingTargetService.saveMovingTarget(movingTarget);
        if (count != 0) {
            log.info("保存动目标成功");
        } else {
            log.error("保存动目标失败");
        }
        return count;
    }

    @RequestMapping(value = "/saveHeatmap", method = {RequestMethod.GET})
    public int saveHeatmap() {
        int count = movingTargetService.saveHeatmap();
        if (count != 0) {
            log.info("保存热力图成功");
        } else {
            log.error("保存热力图失败");
        }
        return count;
    }
    @RequestMapping(value = "/heatmap", method = {RequestMethod.GET})
    public List<Heatmap> getHeatmap() {
        List<Heatmap> heatmap = movingTargetService.getHeatmap();
        if (!heatmap.isEmpty()) {
            log.info("获取" + heatmap.size() + "热力图数据");
        } else {
            log.error("没有热力图数据");
        }
        return heatmap;
    }
//    @RequestMapping(value = "/users", method = { RequestMethod.GET })
//    public List<User> users(UserParamVo param) {
//        return userDao.getUsers(param);
//    }
//
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//
//    @Resource(name = "stringRedisTemplate")
//    ValueOperations<String, String> valueOperationStr;
//
//    @RequestMapping("/redis/string/set")
//    public String setKeyAndValue(String key, String value) {
//        valueOperationStr.set(key, value);
//        return "Set Ok";
//    }
//
//    @RequestMapping("/redis/string/get")
//    public String getKey(String key) {
//        return valueOperationStr.get(key);
//    }
//
//    @Autowired
//    RedisTemplate<Object, Object> redisTemplate;
//
//    @Resource(name = "redisTemplate")
//    ValueOperations<Object, Object> valOps;
//
//    @RequestMapping("/redis/obj/set")
//    public void save(User user) {
//        valOps.set(user.getId(), user);
//    }
//
//    @RequestMapping("/redis/obj/get")
//    public User getPerson(String id) {
//        return (User) valOps.get(id);
//    }

}
