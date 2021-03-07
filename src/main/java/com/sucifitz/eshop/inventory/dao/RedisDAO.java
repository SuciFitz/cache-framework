package com.sucifitz.eshop.inventory.dao;

/**
 * @author S zh
 */
public interface RedisDAO {

    void set(String key, String value);

    String get(String key);
}
