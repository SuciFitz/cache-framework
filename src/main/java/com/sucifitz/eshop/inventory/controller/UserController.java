package com.sucifitz.eshop.inventory.controller;

import com.sucifitz.eshop.inventory.model.User;
import com.sucifitz.eshop.inventory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 用户
 * @author Sucifitz
 * @date 2021/3/7 14:53
 */
@Controller("test")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("getUserInfo")
    @ResponseBody
    public User getUserInfo() {
        return userService.findUser();
    }

    @RequestMapping("getCacheUserInfo")
    @ResponseBody
    public User getCacheUserInfo() {
        return userService.getCacheUserInfo();
    }
}