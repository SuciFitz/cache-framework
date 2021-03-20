package com.sucifitz.eshop.inventory.controller;

import com.sucifitz.eshop.inventory.model.ProductInventory;
import com.sucifitz.eshop.inventory.request.ProductInventoryCacheRefreshRequest;
import com.sucifitz.eshop.inventory.request.ProductInventoryDataBaseUpdateRequest;
import com.sucifitz.eshop.inventory.request.Request;
import com.sucifitz.eshop.inventory.service.ProductInventoryService;
import com.sucifitz.eshop.inventory.service.RequestAsyncProcessService;
import com.sucifitz.eshop.inventory.vo.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 商品库存controller
 *
 * @author Sucifitz
 * @date 2021/3/20 20:29
 */
@Controller
public class ProductInventoryController {

    @Resource
    private ProductInventoryService productInventoryService;

    @Resource
    private RequestAsyncProcessService requestAsyncProcessService;

    /**
     * 更新商品库存
     *
     * @param productInventory 库存
     * @return 结果
     */
    @RequestMapping("updateProductInventory")
    @ResponseBody
    public Response updateProductInventory(ProductInventory productInventory) {
        Response response;
        try {
            Request request = new ProductInventoryDataBaseUpdateRequest(
                    productInventory, productInventoryService);
            requestAsyncProcessService.process(request);
            response = new Response(Response.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            response = new Response(Response.FAILURE);
        }
        return response;
    }

    /**
     * 获取商品库存
     *
     * @param productId 商品id
     * @return 结果
     */
    @RequestMapping("getProductInventory")
    @ResponseBody
    public ProductInventory getProductInventory(Integer productId) {
        ProductInventory productInventory;
        try {
            Request request = new ProductInventoryCacheRefreshRequest(
                    productId, productInventoryService);
            requestAsyncProcessService.process(request);
            // 记录开始时间
            long startTime = System.currentTimeMillis();
            long waitTime = 0L;
            // 尝试读取缓存
            while (waitTime <= 200) {
                // 消耗时间超过200ms退出循环
                productInventory = productInventoryService.getProductInventory(productId);
                if (productInventory == null) {
                    // 没读到缓存，等待20ms继续尝试
                    Thread.sleep(20);
                    waitTime = System.currentTimeMillis() - startTime;
                } else {
                    return productInventory;
                }
            }
            // 读取缓存失败，尝试从数据库获取
            productInventory = productInventoryService.findProductInventory(productId);
            if (productInventory != null) {
                return productInventory;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ProductInventory(productId, -1L);
    }
}