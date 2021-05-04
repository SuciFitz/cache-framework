package com.sucifitz.eshop.cache.kafka;

import com.alibaba.fastjson.JSONObject;
import com.sucifitz.eshop.cache.model.ProductInfo;
import com.sucifitz.eshop.cache.model.ShopInfo;
import com.sucifitz.eshop.cache.service.CacheService;
import com.sucifitz.eshop.cache.spring.SpringContext;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

/**
 * @author szh
 * @date 2021/5/2
 */
public class KafkaMessageProcessor implements Runnable {

    private final KafkaStream<byte[], byte[]> kafkaStream;

    private CacheService cacheService;

    public KafkaMessageProcessor(KafkaStream<byte[], byte[]> kafkaStream) {
        this.kafkaStream = kafkaStream;
        this.cacheService = (CacheService) SpringContext.getApplicationContext()
                .getBean("cacheService");
    }

    @Override
    public void run() {
        ConsumerIterator<byte[], byte[]> it = kafkaStream.iterator();
        while (it.hasNext()) {
            String message = new String(it.next().message());

            // message转为json
            JSONObject msg = JSONObject.parseObject(message);
            // 提取对应服务标识
            String serviceId = msg.getString("serviceId");

            // 如果是商品信息服务
            if ("productInfoService".equals(serviceId)) {
                processProductInfoChangeMessage(msg);
            } else if ("shopInfoService".equals(serviceId)) {

            }
        }
    }

    /**
     * 处理商品变更消息
     *
     * @param object 消息实体
     */
    private void processProductInfoChangeMessage(JSONObject object) {
        // 提取商品id
        Long productId = object.getLong("productId");
        // 调用商品信息服务接口
        // 直接用注释模拟：getProductInfo?productId=1，传递过去，查询数据库
        String productInfoJson = "{\"id\":1,\"name\":\"手机\",\"price\":\"5999\"," +
                "\"pictureList\":\"a.jpg,b.jpg\",\"specification\":\"规格\",\"service\":" +
                "\"一年保修\",\"color\":\"white,black\",\"size\":\"5.5\"}";
        ProductInfo productInfo = JSONObject.parseObject(productInfoJson, ProductInfo.class);

        cacheService.saveProductInfo2LocalCache(productInfo);
        cacheService.saveProductInfo2RedisCache(productInfo);
    }

    /**
     * 处理店铺变更消息
     *
     * @param object 消息实体
     */
    private void processShopInfoChangeMessage(JSONObject object) {
        // 提取店铺id
        Long shopId = object.getLong("shopId");
        // 调用商品信息服务接口
        // 直接用注释模拟：getProductInfo?productId=1，传递过去，查询数据库
        String shopInfoJson = "{\"id\":1,\"name\":\"手机店\",\"level\":\"5\",\"goodCommentRate\":\"0.96\"}";
        ShopInfo shopInfo = JSONObject.parseObject(shopInfoJson, ShopInfo.class);

        cacheService.saveShopInfo2LocalCache(shopInfo);
        cacheService.saveShopInfo2RedisCache(shopInfo);
    }
}
