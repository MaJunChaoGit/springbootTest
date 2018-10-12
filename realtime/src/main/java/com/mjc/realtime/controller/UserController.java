package com.mjc.realtime.controller;

import com.mjc.realtime.entity.User;
import com.mjc.realtime.mapper.UserMapper;
import com.mjc.realtime.vo.UserParamVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserMapper userDao;

    @RequestMapping(value = "/users", method = { RequestMethod.GET })
    public List<User> users(UserParamVo param) {
        return userDao.getUsers(param);
    }

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Resource(name = "stringRedisTemplate")
    ValueOperations<String, String> valueOperationStr;

    @RequestMapping("/redis/string/set")
    public String setKeyAndValue(String key, String value) {
        valueOperationStr.set(key, value);
        return "Set Ok";
    }

    @RequestMapping("/redis/string/get")
    public String getKey(String key) {
        return valueOperationStr.get(key);
    }

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    @Resource(name = "redisTemplate")
    ValueOperations<Object, Object> valOps;

    @RequestMapping("/redis/obj/set")
    public void save(User user) {
        valOps.set(user.getId(), user);
    }

    @RequestMapping("/redis/obj/get")
    public User getPerson(String id) {
        return (User) valOps.get(id);
    }

}
