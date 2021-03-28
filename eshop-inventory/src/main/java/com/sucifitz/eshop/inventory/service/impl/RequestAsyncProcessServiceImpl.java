package com.sucifitz.eshop.inventory.service.impl;

import com.sucifitz.eshop.inventory.request.Request;
import com.sucifitz.eshop.inventory.request.RequestQueue;
import com.sucifitz.eshop.inventory.service.RequestAsyncProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 请求异步处理service实现
 *
 * @author Sucifitz
 * @date 2021/3/20 19:41
 */
@Service("requestAsyncProcessService")
@Slf4j
public class RequestAsyncProcessServiceImpl implements RequestAsyncProcessService {

    @Override
    public void process(Request request) {
        // 做请求路由，根据处理的商品id，路由到对应的内存队列中
        ArrayBlockingQueue<Request> queue = getRouteQueue(request.getProductId());
        // 将请求放入对应的队列中，完成路由操作
        try {
            queue.put(request);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer getProductId() {
        return null;
    }

    /**
     * 获取路由内存队列
     *
     * @param productId 商品id
     * @return 路由队列
     */
    private ArrayBlockingQueue<Request> getRouteQueue(Integer productId) {
        RequestQueue requestQueue = RequestQueue.getInstance();
        // 获取productId的哈希值
        String key = String.valueOf(productId);
        int h;
        int hash = key == null ? 0 : (h = key.hashCode()) ^ (h >>> 16);
        // 对哈希值取模，将hash值路由到指定的内存队列中
        int index = (requestQueue.queueSize() - 1) & hash;
        log.debug("路由内存队列，商品id={}，队列索引={}", productId, index);
        return requestQueue.getQueue(index);
    }
}