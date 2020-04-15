package org.ccit.com;

import java.util.concurrent.locks.Lock;

public class LockCondition1 {

    /**
     * synchronized关键字无法破坏不可抢占条件
     *  1.能够响应中断。synchronized的问题是，持有锁A后，如果尝试获取锁B失败，那么线程就进入阻塞状态，一旦发生死锁，就没有任何机会来唤醒阻塞的线程。
     *    但如果阻塞的线程能够响应中断信号，也就是说当我们给阻塞的线程
     */
    public static void main(String[] args) {

    }

}
