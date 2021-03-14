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
}
