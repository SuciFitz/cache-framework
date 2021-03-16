package com.sucifitz.eshop.inventory.dao.impl;

import com.sucifitz.eshop.inventory.dao.RedisDAO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
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

    private final ValueOperations<String, String> valueOperations;

    public RedisDAOImpl(RedisTemplate<String, String> redisTemplate) {
        this.valueOperations = redisTemplate.opsForValue();
    }

    @Override
    public void set(String key, String value) {
        valueOperations.set(key, value);
    }

    @Override
    public String get(String key) {
        return valueOperations.get(key);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}