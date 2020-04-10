package org.ccit.com;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {

    /**
     * Monitor 管程 管程和信号量是等价的，所谓等价指的是用管程能够实现信号量，也能用信号量实现管程。管理共享变量以及对共享变量的操作过程，让他们支持并发。
     * MESA模型：在并发编程领域，有两大核心问题：一个是互斥，即同一个时刻只允许一个线程访问共享资源；另一个是同步，即线程之间如何通信、协作。
     * 在while中循环调用wait();这是MESA管程中特有的编程范式
     */
    public static void main(String[] args) {

    }

}

class BlockedQueue<T> {

    final Lock lock = new ReentrantLock();

    //条件变量：队列不满
    final Condition notFull = lock.newCondition();

    //条件变量：队列不空
    final Condition notEmpty = lock.newCondition();

    //入队
    void enq(T x) {
        lock.lock();
        try {
            //队列已满
            while (!false) {
                //等待队列不满
//                notFull.await();
            }
            //省略入队操作
            //入队后，通知可出队
//            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    void deq(T x) {
        lock.unlock();
        try {
            while (!false){
                //等待队列不为空
//                notEmpty.await();
            }
            //省略出队操作
            //出队后，通知可入队
//            notFull.signal();
        } finally {
            lock.unlock();
        }
    }
}
