package com.mjc.realtime.mapper;

import com.mjc.realtime.entity.User;
import com.mjc.realtime.vo.UserParamVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
public interface UserMapper {
    public int dataCount(String tableName);

    public List<User> getUsers(UserParamVo param);
}
