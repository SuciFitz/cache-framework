package com.sucifitz.eshop.cache.service.impl;

import com.sucifitz.eshop.cache.model.ProductInfo;
import com.sucifitz.eshop.cache.service.CacheService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 缓存服务实现类
 *
 * @author Sucifitz
 * @date 2021/3/28 19:51
 */
@Service("cacheServiceImpl")
public class CacheServiceImpl implements CacheService {

    private static final String CACHE_NAME = "local";

    @CachePut(value = CACHE_NAME, key = "'key_' + #productInfo.getId()")
    public ProductInfo saveLocalCache(ProductInfo productInfo) {
        return productInfo;
    }

    @Cacheable(value = CACHE_NAME, key = "'key_' + #id")
    public ProductInfo getLocalCache(Long id) {
        // 没取到返回null
        return null;
    }
}