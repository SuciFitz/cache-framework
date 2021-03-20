package com.sucifitz.eshop.inventory.service;

import com.sucifitz.eshop.inventory.model.ProductInventory;

/**
 * 商品库存service接口
 *
 * @author S zh
 */
public interface ProductInventoryService {

    /**
     * 更新库存数量
     *
     * @param productInventory 库存
     */
    void updateProductInventory(ProductInventory productInventory);

    /**
     * 删除redis中的库存缓存
     *
     * @param productInventory 库存
     */
    void removeProductInventoryCache(ProductInventory productInventory);

    /**
     * 根据商品id查询库存信息
     *
     * @param productId 商品id
     * @return 库存
     */
    ProductInventory findProductInventory(Integer productId);

    /**
     * 设置商品库存缓存
     *
     * @param productInventory 库存
     */
    void setProductInventoryCache(ProductInventory productInventory);

    /**
     * 获取商品库存缓存
     *
     * @param productId 商品id
     * @return 库存
     */
    ProductInventory getProductInventory(Integer productId);
}
