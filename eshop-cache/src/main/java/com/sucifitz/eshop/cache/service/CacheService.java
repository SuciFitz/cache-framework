package com.sucifitz.eshop.cache.service;

import com.sucifitz.eshop.cache.model.ProductInfo;

/**
 * 缓存service接口
 *
 * @author Sucifitz
 * @date 2021/3/28 19:49
 */
public interface CacheService {

    /**
     * 商品信息保存到本地缓存
     *
     * @param productInfo 商品信息
     * @return 商品信息
     */
    ProductInfo saveLocalCache(ProductInfo productInfo);

    /**
     * 从缓存中获取商品信息
     *
     * @param id 商品id
     * @return 商品信息
     */
    ProductInfo getLocalCache(Long id);
}