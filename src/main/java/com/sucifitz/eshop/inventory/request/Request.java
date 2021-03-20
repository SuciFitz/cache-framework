package com.sucifitz.eshop.inventory.request;

/**
 * 请求接口
 *
 * @author S zh
 */
public interface Request {

    void process();

    /**
     * 获取商品id
     *
     * @return 商品id
     */
    Integer getProductId();
}
