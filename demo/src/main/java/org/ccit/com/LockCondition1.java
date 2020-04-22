package org.ccit.com;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockCondition1 {

    /**
     * synchronized关键字无法破坏不可抢占条件
     *  1.能够响应中断。synchronized的问题是，持有锁A后，如果尝试获取锁B失败，那么线程就进入阻塞状态，一旦发生死锁，就没有任何机会来唤醒阻塞的线程。
     *    但如果阻塞的线程能够响应中断信号，也就是说当我们给阻塞的线程发送中断信号的时候，能够唤醒它，那么它就有机会释放A锁。
     *  2.支持超时。如果线程在一段时间之内没有获取到锁，不是进入阻塞状态，而是返回一个错误，那这个线程也有机会释放曾经持有的锁。
     *  3.非阻塞地获取锁。如果尝试获取锁失败，并不进入阻塞状态，而是直接返回，那这个线程也有机会释放曾经持有的锁。
     */
    public static void main(String[] args) throws Exception{
        Lock lock = new ReentrantLock();
        //支持中断的api
        lock.lockInterruptibly();

        //支持超时
        lock.tryLock(1000, TimeUnit.DAYS);

        //支持非阻塞地获取锁
        lock.tryLock();
    }

}
