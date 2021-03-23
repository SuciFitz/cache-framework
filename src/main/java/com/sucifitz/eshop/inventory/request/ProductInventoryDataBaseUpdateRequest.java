package com.sucifitz.eshop.inventory.request;

import com.sucifitz.eshop.inventory.model.ProductInventory;
import com.sucifitz.eshop.inventory.service.ProductInventoryService;

/**
 * 数据更新请求
 *
 * @author Sucifitz
 * @date 2021/3/14 21:17
 */
public class ProductInventoryDataBaseUpdateRequest implements Request {

    /**
     * 商品库存
     */
    private ProductInventory productInventory;

    /**
     * 商品库存service
     */
    private ProductInventoryService productInventoryService;

    public ProductInventoryDataBaseUpdateRequest(ProductInventory productInventory,
                                                 ProductInventoryService productInventoryService) {
        this.productInventory = productInventory;
        this.productInventoryService = productInventoryService;
    }

    @Override
    public void process() {
        // 删除redis中的缓存
        productInventoryService.removeProductInventoryCache(productInventory);
        // 修改数据库库存
        productInventoryService.updateProductInventory(productInventory);
    }

    /**
     * 获取商品id
     *
     * @return 商品id
     */
    @Override
    public Integer getProductId() {
        return productInventory.getProductId();
    }

    @Override
    public boolean isForceRefresh() {
        return false;
    }
}