package com.sucifitz.eshop.inventory.thread;

import com.sucifitz.eshop.inventory.request.Request;
import com.sucifitz.eshop.inventory.request.RequestQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 请求处理线程池
 *
 * @author Sucifitz
 * @date 2021/3/14 20:04
 */
public class RequestProcessorThreadPool {

    /**
     * 在实际项目中，你设置的线程大小是多少，每个线程监控的内存队列大小是多少
     * 都可以做到一个外部文件中
     * 此处简化
     */
    private ExecutorService threadPool = Executors.newFixedThreadPool(10);

    /// TODO: 2021/3/14 转为ThreadPoolExecutor创建线程池
    // private ExecutorService fixedThreadPool(int nThreads) {
    //     return new ThreadPoolExecutor(nThreads, nThreads,
    //             0L, TimeUnit.MILLISECONDS,
    //             new LinkedBlockingQueue<Runnable>());
    // }


    public RequestProcessorThreadPool() {
        RequestQueue queues = RequestQueue.getInstance();
        // 初始化时创建10个请求队列，并把请求队列添加到内存队列中，并把内存队列与线程池绑定
        for (int i = 0; i < 10; i++) {
            ArrayBlockingQueue<Request> queue = new ArrayBlockingQueue<>(100);
            queues.addQueue(queue);
            threadPool.submit(new RequestProcessorThread(queue));
        }
    }

    /**
     * 单例实现方式：静态内部类
     */
    private static class Singleton {

        private static final RequestProcessorThreadPool INSTANCE;

        static {
            INSTANCE = new RequestProcessorThreadPool();
        }

        public static RequestProcessorThreadPool getInstance() {
            return INSTANCE;
        }
    }

    /**
     * jvm保证多线程并发安全
     * 内部类初始化只会发生一次
     *
     * @return x
     */
    public static RequestProcessorThreadPool getInstance() {
        return Singleton.getInstance();
    }

    /**
     * 初始化的便捷方法
     *
     * @return 请求处理线程池
     */
    public static void init() {
        getInstance();
    }
}