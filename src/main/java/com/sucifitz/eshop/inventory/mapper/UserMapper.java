package com.sucifitz.eshop.inventory.mapper;

import com.sucifitz.eshop.inventory.model.User;

/**
 * 测试用户mapper接口
 *
 * @author S zh
 */
public interface UserMapper {

    /**
     * 查询用户信息
     *
     * @return 用户实体类
     */
    public User findUserInfo();
}
