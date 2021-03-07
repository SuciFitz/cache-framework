package com.sucifitz.eshop.inventory.service;

import com.sucifitz.eshop.inventory.model.User;

/**
 * 用户服务接口
 *
 * @author S zh
 */
public interface UserService {

    /**
     * 查询用户信息
     *
     * @return 用户信息
     */
    User findUser();

    /**
     * 获取缓存的用户信息
     *
     * @return 用户信息
     */
    User getCacheUserInfo();
}
