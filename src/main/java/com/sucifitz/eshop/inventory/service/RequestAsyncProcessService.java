package com.sucifitz.eshop.inventory.service;

import com.sucifitz.eshop.inventory.request.Request;

/**
 * 请求异步执行
 *
 * @author Sucifitz
 * @date 2021/3/20 19:35
 */
public interface RequestAsyncProcessService {

    void process(Request request);

    /**
     * 获取商品id
     *
     * @return 商品id
     */
    Integer getProductId();
}
