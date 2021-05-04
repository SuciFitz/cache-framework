package com.sucifitz.eshop.cache.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sucifitz.eshop.cache.model.ProductInfo;
import com.sucifitz.eshop.cache.model.ShopInfo;
import com.sucifitz.eshop.cache.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

/**
 * 缓存服务实现类
 *
 * @author Sucifitz
 * @date 2021/3/28 19:51
 */
@Service("cacheServiceImpl")
public class CacheServiceImpl implements CacheService {

    private static final String CACHE_NAME = "local";

    private JedisCluster jedisCluster;

    @Override
    @CachePut(value = CACHE_NAME, key = "'key_' + #productInfo.getId()")
    public ProductInfo saveLocalCache(ProductInfo productInfo) {
        return productInfo;
    }

    @Override
    @Cacheable(value = CACHE_NAME, key = "'key_' + #id")
    public ProductInfo getLocalCache(Long id) {
        // 没取到返回null
        return null;
    }

    @Override
    @Cacheable(value = CACHE_NAME, key = "'product_info_' + #productInfo.getId()")
    public ProductInfo saveProductInfo2LocalCache(ProductInfo productInfo) {
        return productInfo;
    }

    @Override
    public void saveProductInfo2RedisCache(ProductInfo productInfo) {
        String key = "product_info_" + productInfo.getId();
        jedisCluster.set(key, JSONObject.toJSONString(productInfo));
    }

    @Override
    @Cacheable(value = CACHE_NAME, key = "'shop_info_' + #shopInfo.getId()")
    public ShopInfo saveShopInfo2LocalCache(ShopInfo shopInfo) {
        return shopInfo;
    }

    @Override
    public void saveShopInfo2RedisCache(ShopInfo shopInfo) {
        String key = "shop_info_" + shopInfo.getId();
        jedisCluster.set(key, JSONObject.toJSONString(shopInfo));
    }
}