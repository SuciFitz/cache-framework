package com.sucifitz.eshop.cache.service;

import com.sucifitz.eshop.cache.model.ProductInfo;
import com.sucifitz.eshop.cache.model.ShopInfo;

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

    /**
     * 将商品信息保存到本地ehcache缓存中
     *
     * @param productInfo 商品信息
     */
    ProductInfo saveProductInfo2LocalCache(ProductInfo productInfo);

    /**
     * 将商品信息保存到redis中
     *
     * @param productInfo 商品信息
     */
    void saveProductInfo2RedisCache(ProductInfo productInfo);

    /**
     * 将店铺信息保存到本地ehcache缓存中
     *
     * @param shopInfo 店铺信息
     * @return
     */
    ShopInfo saveShopInfo2LocalCache(ShopInfo shopInfo);

    /**
     * 将店铺信息保存到redis中
     *
     * @param shopInfo 店铺信息
     */
    void saveShopInfo2RedisCache(ShopInfo shopInfo);

    /**
     * 从本地ehcache中获取商品信息
     *
     * @param productId 商品id
     * @return 商品信息
     */
    ProductInfo getProductInfoFromLocalCache(Long productId);

    /**
     * 从本地ehcache中获取店铺信息
     *
     * @param shopId 店铺id
     * @return 商品信息
     */
    ShopInfo getShopInfoFromLocalCache(Long shopId);
}