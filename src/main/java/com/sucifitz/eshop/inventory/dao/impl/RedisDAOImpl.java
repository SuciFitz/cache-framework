package com.sucifitz.eshop.inventory.dao.impl;

import com.sucifitz.eshop.inventory.dao.RedisDAO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author Sucifitz
 * @date 2021/3/7 15:39
 */
@Repository("redisDAO")
public class RedisDAOImpl implements RedisDAO {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}