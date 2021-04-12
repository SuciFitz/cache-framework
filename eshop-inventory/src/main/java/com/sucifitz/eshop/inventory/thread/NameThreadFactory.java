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

    /**
     * 线程池编号
     */
    private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);

    /**
     * 线程组
     */
    private final ThreadGroup group;

    /**
     * 线程池中的线程编号
     */
    private final AtomicInteger num = new AtomicInteger(1);

    /**
     * 线程池中线程名称前缀
     */
    private final String namePrefix;

    NameThreadFactory(String name) {
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        if (null == name || name.isEmpty()) {
            name = "pool";
        }
        namePrefix = name + "-" + POOL_NUMBER.getAndIncrement() + "-thread-";
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r, namePrefix + num.getAndIncrement(), 0);
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }
}