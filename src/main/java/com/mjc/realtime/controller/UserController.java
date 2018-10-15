package com.mjc.realtime.controller;

import com.mjc.realtime.entity.MovingTarget;
import com.mjc.realtime.service.IMovingTargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8085", maxAge = 3600)
@RestController
public class UserController {

    @Autowired
    private IMovingTargetService movingTargetService;

    @RequestMapping(value = "/movingTargets", method = {RequestMethod.GET})
    public List<MovingTarget> movingTargets(){
        return movingTargetService.getMovingTargetInfo();
    }

    @RequestMapping(value = "/saveMovingTarget", method = {RequestMethod.POST})
    public int saveMovingTarget(@RequestBody MovingTarget movingTarget) {
        return movingTargetService.saveMovingTarget(movingTarget);
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
