package com.grain.mall.controller;

import java.util.concurrent.*;

/**
 * @author gongjin
 * 线程池的使用
 */
public class ThreadPool {
    public static void main(String[] args) {
        // 创建一个线程池
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        ThreadPools threadPool = new ThreadPools();
//        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5,5,0L,TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>());
        // 为线程池中的线程分配任务
        for (int i = 0,j = 10; i < j; i++) {
            executorService.submit(threadPool);
        }
        // 关闭线程池
        executorService.shutdown();
    }
}

class ThreadPools implements Runnable{
    int a = 1;
    @Override
    public void run() {
        System.out.println("AA - " + a++);
    }
}
