package com.sucifitz.eshop.cache.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

/**
 * redis工具类
 *
 * @author szh
 * @date 2021/5/7
 */
@Repository("redisUtil")
public class RedisUtil {

    private final RedisTemplate<String, String> redisTemplate;

    private final ValueOperations<String, String> valueOperations;

    public RedisUtil(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.valueOperations = redisTemplate.opsForValue();
    }

    public void set(String key, String value) {
        valueOperations.set(key, value);
    }

    public String get(String key) {
        return valueOperations.get(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
