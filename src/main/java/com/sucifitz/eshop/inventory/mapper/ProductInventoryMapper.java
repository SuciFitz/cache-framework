package com.sucifitz.eshop.inventory.mapper;

import com.sucifitz.eshop.inventory.model.ProductInventory;

/**
 * 库存数量Mapper
 *
 * @author S zh
 */
public interface ProductInventoryMapper {

    /**
     * 更新库存数量
     *
     * @param productInventory 库存
     */
    void updateProductInventory(ProductInventory productInventory);

    /**
     * 根据商品id查询库存信息
     *
     * @param productId 商品id
     * @return 库存
     */
    ProductInventory findProductInventory(Integer productId);
}
