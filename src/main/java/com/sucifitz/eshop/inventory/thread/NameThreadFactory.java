package com.sucifitz.eshop.inventory.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义线程工厂，为线程命名
 *
 * @author Sucifitz
 * @date 2021/3/20 13:59
 */
public class NameThreadFactory implements ThreadFactory {

    private final AtomicInteger num = new AtomicInteger();

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, "thread-" + num.getAndIncrement());
    }
}