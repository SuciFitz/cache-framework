package com.sucifitz.eshop.inventory.service.impl;

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

    @Override
    public User findUser() {
        return userMapper.findUserInfo();
    }
}