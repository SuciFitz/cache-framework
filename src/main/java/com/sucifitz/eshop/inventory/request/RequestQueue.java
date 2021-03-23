package com.sucifitz.eshop.inventory.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 请求内存队列
 *
 * @author Sucifitz
 * @date 2021/3/14 21:01
 */
public class RequestQueue {

    /**
     * 内存队列
     */
    private final List<ArrayBlockingQueue<Request>> queues = new ArrayList<>();

    /**
     * 标识位map
     */
    private final Map<Integer, Boolean> flagMap = new ConcurrentHashMap<>();

    /**
     * 单例实现方式：静态内部类
     */
    private static class Singleton {

        private static final RequestQueue INSTANCE;

        static {
            INSTANCE = new RequestQueue();
        }

        /**
         * 单例模式
         * 默认构造器私有化
         */
        private Singleton() {
        }

        public static RequestQueue getInstance() {
            return INSTANCE;
        }
    }

    /**
     * 单例模式
     * 默认构造器私有化
     */
    private RequestQueue() {
    }

    /**
     * jvm保证多线程并发安全
     * 内部类初始化只会发生一次
     *
     * @return x
     */
    public static RequestQueue getInstance() {
        return Singleton.getInstance();
    }

    /**
     * 初始化的便捷方法
     *
     * @return 内存队列
     */
    public static RequestQueue init() {
        return getInstance();
    }

    /**
     * 添加一个内存队列
     *
     * @param queue 内存队列
     */
    public void addQueue(ArrayBlockingQueue<Request> queue) {
        this.queues.add(queue);
    }

    /**
     * 获取内存队列的数量
     *
     * @return 内存队列的数量
     */
    public int queueSize() {
        return queues.size();
    }

    /**
     * 获取内存队列
     *
     * @param index 索引
     * @return 内存队列
     */
    public ArrayBlockingQueue<Request> getQueue(int index) {
        return queues.get(index);
    }

    /**
     * 获取标识位map
     *
     * @return 标识位map
     */
    public Map<Integer, Boolean> getFlagMap() {
        return flagMap;
    }
}