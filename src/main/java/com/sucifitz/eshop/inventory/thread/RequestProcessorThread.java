package com.sucifitz.eshop.inventory.thread;

import com.sucifitz.eshop.inventory.request.Request;

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
    public Boolean call() throws Exception {
        try {
            while (true) {
                // blocking 阻塞队列
                Request request = queue.take();
                // 执行request操作
                request.process();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}