package com.grain.mall.controller;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author gongjin
 */
public class Study {
    public static void main(String[] args) {
//        new TestLoop().testLoop();
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    new testReadWriteLock().testReadWriteLock();
                }
            }).start();
        }
    }
}

class testReadWriteLock {
    public void testReadWriteLock() {
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        readWriteLock.readLock().lock();
        try{
            System.out.println(readWriteLock.readLock().toString());
        } finally {
            readWriteLock.readLock().unlock();
        }

        readWriteLock.writeLock().lock();
        try{
            System.out.println(readWriteLock.writeLock().toString());
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }
}

/**
 * 启动三个线程，交替打印ABC三个字母10次；
 * 用等待唤醒机制实现
 */
class TestLoop {
    public void testLoop() {
        loop loop = new loop();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i < 11; i++) {
                    loop.loopA(i);
                }
            }
        }, "A").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i < 11; i++) {
                    loop.loopB(i);
                }
            }
        }, "B").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i < 11; i++) {
                    loop.loopC(i);
                    System.out.println("-------------------------------");
                }
            }
        }, "C").start();
    }
}
class loop {
    private int num = 1;
    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    public void loopA(int a) {
        lock.lock();
        try {
            if (num != 1) {
                condition1.await();
            }
            System.out.println("A: " + a);
            num = 2;
            condition2.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void loopB(int b) {
        lock.lock();
        try {
            if (num != 2) {
                condition2.await();
            }
            System.out.println("B: " + b);
            num = 3;
            condition3.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void loopC(int c) {
        lock.lock();
        try {
            if (num != 3) {
                condition3.await();
            }
            System.out.println("C: " + c);
            num = 1;
            condition1.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
