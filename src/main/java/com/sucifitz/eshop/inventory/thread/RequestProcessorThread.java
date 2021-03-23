package com.sucifitz.eshop.inventory.thread;

import com.sucifitz.eshop.inventory.request.ProductInventoryCacheRefreshRequest;
import com.sucifitz.eshop.inventory.request.ProductInventoryDataBaseUpdateRequest;
import com.sucifitz.eshop.inventory.request.Request;
import com.sucifitz.eshop.inventory.request.RequestQueue;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

/**
 * 执行请求的工作线程
 *
 * @author Sucifitz
 * @date 2021/3/14 20:57
 */
public class RequestProcessorThread implements Callable<Boolean> {

    /**
     * 监控的内存队列
     */
    private final ArrayBlockingQueue<Request> queue;

    public RequestProcessorThread(ArrayBlockingQueue<Request> queue) {
        this.queue = queue;
    }

    @Override
    public Boolean call() {
        try {
            while (true) {
                // blocking 阻塞队列
                Request request = queue.take();
                boolean forceRefresh = request.isForceRefresh();
                // 如果不是强制刷新，需要去重
                if (!forceRefresh) {
                    // 先做读请求去重
                    RequestQueue requestQueue = RequestQueue.getInstance();
                    Map<Integer, Boolean> flagMap = requestQueue.getFlagMap();

                    if (request instanceof ProductInventoryDataBaseUpdateRequest) {
                        // 如果是更新数据库请求，就把productId对应的标识设为true
                        flagMap.put(request.getProductId(), true);
                    } else if (request instanceof ProductInventoryCacheRefreshRequest) {
                        // 如果是缓存刷新请求，如果标识不为空且是true，说明之前有一个更新请求
                        Boolean flag = flagMap.putIfAbsent(request.getProductId(), false);
                        if (flag != null && flag) {
                            flagMap.put(request.getProductId(), false);
                        }
                        // 如果是缓存刷新请求，发现标识不为空，但标识为false
                        // 说明前面已经有数据库更新请求+缓存刷新请求了
                        if (flag != null && !flag) {
                            return true;
                        }
                    }
                }
                System.out.println("工作线程处理请求：商品id=" + request.getProductId());
                // 执行request操作
                request.process();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}