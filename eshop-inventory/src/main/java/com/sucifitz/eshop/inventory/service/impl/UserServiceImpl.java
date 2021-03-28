package com.sucifitz.eshop.inventory.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sucifitz.eshop.inventory.dao.RedisDAO;
import com.sucifitz.eshop.inventory.mapper.UserMapper;
import com.sucifitz.eshop.inventory.model.User;
import com.sucifitz.eshop.inventory.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Sucifitz
 * @date 2021/3/7 14:50
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisDAO redisDAO;

    @Override
    public User findUser() {
        return userMapper.findUserInfo();
    }

    @Override
    public User getCacheUserInfo() {
        redisDAO.set("userCache", "{\"name\":\"李四\",\"age\":28}");
        String userJson = redisDAO.get("userCache");
        JSONObject object = JSONObject.parseObject(userJson);
        User user = new User();
        user.setName(object.getString("name"));
        user.setAge(object.getInteger("age"));
        return user;
    }
}