package com.sucifitz.eshop.inventory.model;

import lombok.Data;

/**
 * 库存数量 model
 *
 * @author Sucifitz
 * @date 2021/3/14 21:34
 */
@Data
public class ProductInventory {

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 库存
     */
    private Long inventoryCnt;

    public ProductInventory(Integer productId, Long inventoryCnt) {
        this.productId = productId;
        this.inventoryCnt = inventoryCnt;
    }
}