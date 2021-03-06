package com.sucifitz.eshop.inventory.service.impl;

import com.sucifitz.eshop.inventory.dao.RedisDAO;
import com.sucifitz.eshop.inventory.mapper.ProductInventoryMapper;
import com.sucifitz.eshop.inventory.model.ProductInventory;
import com.sucifitz.eshop.inventory.service.ProductInventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * 商品库存实现类
 *
 * @author Sucifitz
 * @date 2021/3/14 21:42
 */
@Service("productInventoryService")
@Slf4j
public class ProductInventoryServiceImpl implements ProductInventoryService {

    @Resource
    private ProductInventoryMapper productInventoryMapper;

    @Resource
    private RedisDAO redisDAO;

    @Override
    public void updateProductInventory(ProductInventory productInventory) {
        productInventoryMapper.updateProductInventory(productInventory);
        log.debug("已修改库存，商品id={}，商品库存数量={}", productInventory.getProductId(), productInventory.getInventoryCnt());
    }

    @Override
    public void removeProductInventoryCache(ProductInventory productInventory) {
        String key = "product:inventory:" + productInventory.getProductId();
        redisDAO.delete(key);
        log.debug("已删除redis中的缓存，key={}", key);
    }

    @Override
    public ProductInventory findProductInventory(Integer productId) {
        return productInventoryMapper.findProductInventory(productId);
    }

    @Override
    public void setProductInventoryCache(ProductInventory productInventory) {
        String key = "product:inventory:" + productInventory.getProductId();
        redisDAO.set(key, String.valueOf(productInventory.getInventoryCnt()));
        log.debug("已更新商品库存缓存，商品id={}，商品库存数量={}，key={}", productInventory.getProductId(), productInventory.getInventoryCnt(), key);
    }

    @Override
    public ProductInventory getProductInventory(Integer productId) {
        String key = "product:inventory:" + productId;
        String res = redisDAO.get(key);
        long inventoryCnt;
        if (StringUtils.hasLength(res)) {
            try {
                inventoryCnt = Long.parseLong(res);
                return new ProductInventory(productId, inventoryCnt);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}