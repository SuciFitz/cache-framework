package com.sucifitz.eshop.inventory.request;

import com.sucifitz.eshop.inventory.model.ProductInventory;
import com.sucifitz.eshop.inventory.service.ProductInventoryService;
import lombok.extern.slf4j.Slf4j;

/**
 * 数据更新请求
 *
 * @author Sucifitz
 * @date 2021/3/14 21:17
 */
@Slf4j
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
        log.debug("数据更新开始执行，商品id={}，商品库存数量={}", productInventory.getProductId(), productInventory.getInventoryCnt());
        // 删除redis中的缓存
        productInventoryService.removeProductInventoryCache(productInventory);
        /// 测试代码 为了模拟先删除了缓存，没更新数据库，这时候读请求来了，可以sleep一下。
        // try {
        //     Thread.sleep(10000);
        //     log.debug("睡醒了");
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
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