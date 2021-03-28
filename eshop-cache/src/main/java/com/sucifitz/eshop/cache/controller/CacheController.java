package com.sucifitz.eshop.cache.controller;

import com.sucifitz.eshop.cache.model.ProductInfo;
import com.sucifitz.eshop.cache.service.CacheService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 缓存controller
 *
 * @author Sucifitz
 * @date 2021/3/28 19:55
 */
@Controller
public class CacheController {

    @Resource
    private CacheService cacheService;

    @ResponseBody
    @RequestMapping("testPutCache")
    public String testPutCache(ProductInfo productInfo) {
        cacheService.saveLocalCache(productInfo);
        return "success";
    }

    @ResponseBody
    @RequestMapping("testGetCache")
    public ProductInfo testGetCache(Long id) {
        return cacheService.getLocalCache(id);
    }
}