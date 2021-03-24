package com.sucifitz.eshop.inventory.controller;

import com.sucifitz.eshop.inventory.model.ProductInventory;
import com.sucifitz.eshop.inventory.request.ProductInventoryCacheRefreshRequest;
import com.sucifitz.eshop.inventory.request.ProductInventoryDataBaseUpdateRequest;
import com.sucifitz.eshop.inventory.request.Request;
import com.sucifitz.eshop.inventory.service.ProductInventoryService;
import com.sucifitz.eshop.inventory.service.RequestAsyncProcessService;
import com.sucifitz.eshop.inventory.vo.Response;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
        log.debug("更新商品库存请求，商品id：{}，商品库存：{}", productInventory.getProductId(), productInventory.getInventoryCnt());
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
        log.debug("收到商品库存读请求，商品id={}", productId);
        ProductInventory productInventory;
        try {
            Request request = new ProductInventoryCacheRefreshRequest(
                    productId, productInventoryService, false);
            requestAsyncProcessService.process(request);
            // 记录开始时间
            long startTime = System.currentTimeMillis();
            long waitTime = 0L;
            // 尝试读取缓存
            while (waitTime <= 200) {
                // 200ms内，等待之前的请求刷新缓存，直到读取成功
                // log.debug("waitTime={}", waitTime);
                // 消耗时间超过200ms退出循环
                productInventory = productInventoryService.getProductInventory(productId);
                if (productInventory == null) {
                    // 没读到缓存，等待20ms继续尝试
                    Thread.sleep(20);
                    waitTime = System.currentTimeMillis() - startTime;
                } else {
                    log.debug("在200ms内读取到了商品库存缓存，商品id={}，商品库存={}", productId, productInventory.getInventoryCnt());
                    return productInventory;
                }
            }
            // 读取缓存失败，尝试从数据库获取
            productInventory = productInventoryService.findProductInventory(productId);
            if (productInventory != null) {
                request = new ProductInventoryCacheRefreshRequest(
                        productId, productInventoryService, false);
                // 读操作之后，并行刷新缓存，保证数据一致
                requestAsyncProcessService.process(request);
                return productInventory;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ProductInventory(productId, -1L);
    }
}