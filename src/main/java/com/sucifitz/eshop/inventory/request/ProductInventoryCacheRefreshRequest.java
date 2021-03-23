package com.sucifitz.eshop.inventory.request;

import com.sucifitz.eshop.inventory.model.ProductInventory;
import com.sucifitz.eshop.inventory.service.ProductInventoryService;

/**
 * 重新加载商品库存缓存
 *
 * @author Sucifitz
 * @date 2021/3/14 21:53
 */
public class ProductInventoryCacheRefreshRequest implements Request {

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 商品库存service
     */
    private ProductInventoryService productInventoryService;

    /**
     * 是否强制刷新缓存
     */
    private boolean forceRefresh;

    public ProductInventoryCacheRefreshRequest(Integer productId,
                                               ProductInventoryService productInventoryService,
                                               boolean forceRefresh) {
        this.productId = productId;
        this.productInventoryService = productInventoryService;
        this.forceRefresh = forceRefresh;
    }

    @Override
    public void process() {
        // 查询最新的商品库存
        ProductInventory inventory = productInventoryService.findProductInventory(productId);
        System.out.println("已查询到库存数量，商品id" + productId);
        // 将库存更新到缓存中
        productInventoryService.setProductInventoryCache(inventory);
    }

    /**
     * 获取商品id
     *
     * @return 商品id
     */
    @Override
    public Integer getProductId() {
        return productId;
    }

    @Override
    public boolean isForceRefresh() {
        return forceRefresh;
    }

}