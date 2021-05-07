package com.sucifitz.eshop.cache.kafka;

import com.alibaba.fastjson.JSONObject;
import com.sucifitz.eshop.cache.model.ProductInfo;
import com.sucifitz.eshop.cache.model.ShopInfo;
import com.sucifitz.eshop.cache.service.CacheService;
import com.sucifitz.eshop.cache.spring.SpringContext;
import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * @author szh
 * @date 2021/5/2
 */
@Slf4j
public class KafkaMessageProcessor implements Runnable {

    private final KafkaStream<byte[], byte[]> kafkaStream;

    @Resource
    private final CacheService cacheService;

    public KafkaMessageProcessor(KafkaStream<byte[], byte[]> kafkaStream) {
        this.kafkaStream = kafkaStream;
        this.cacheService = (CacheService) SpringContext.getApplicationContext()
                .getBean("cacheService");
    }

    @Override
    public void run() {
        for (MessageAndMetadata<byte[], byte[]> messageAndMetadata : kafkaStream) {
            String message = new String(messageAndMetadata.message());

            // message转为json
            JSONObject msg;
            try {
                msg = JSONObject.parseObject(message);
                // 提取对应服务标识
                String serviceId = msg.getString("serviceId");

                // 如果是商品信息服务
                if ("productInfoService".equals(serviceId)) {
                    processProductInfoChangeMessage(msg);
                } else if ("shopInfoService".equals(serviceId)) {
                    processShopInfoChangeMessage(msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
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
                "\"一年保修\",\"color\":\"white,black\",\"size\":\"5.5\",\"shopId\":1}";
        ProductInfo productInfo = JSONObject.parseObject(productInfoJson, ProductInfo.class);

        cacheService.saveProductInfo2LocalCache(productInfo);
        log.debug("获取刚保存到本地缓存的商品信息：{}", cacheService.getProductInfoFromLocalCache(productId));
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
        String shopInfoJson = "{\"id\":1,\"name\":\"手机店\",\"level\":\"5\",\"goodCommentRate\"" +
                ":\"0.96\"}";
        ShopInfo shopInfo = JSONObject.parseObject(shopInfoJson, ShopInfo.class);

        cacheService.saveShopInfo2LocalCache(shopInfo);
        log.debug("获取刚保存到本地缓存的店铺信息：{}", cacheService.getShopInfoFromLocalCache(shopId));
        cacheService.saveShopInfo2RedisCache(shopInfo);
    }
}
