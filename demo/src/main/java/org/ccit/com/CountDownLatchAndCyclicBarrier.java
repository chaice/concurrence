package org.ccit.com;

import java.util.Vector;
import java.util.concurrent.*;

public class CountDownLatchAndCyclicBarrier {

    final Executor executor = Executors.newFixedThreadPool(2);

    final CountDownLatch countDownLatch = new CountDownLatch(2);

    /**
     * CountDownLatch 主要用来一个线程等待多个线程的场景
     * CyclicBarrier 是一组线程之间相互等待
     */
    public static void main(String[] args) {

    }

    void accountCheck() throws InterruptedException {
        //存在未对账订单
        while (true) {
            executor.execute(() -> {
                //执行逻辑
                countDownLatch.countDown();
            });

            executor.execute(() -> {
                //执行逻辑
                countDownLatch.countDown();
            });

            countDownLatch.await();
        }
    }
}

class AccountCheck<P, D> {

    //订单队列
    Vector<P> pos;

    //派送单队列
    Vector<D> dos;

    final Executor executor = Executors.newFixedThreadPool(1);

    final CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () -> {
        executor.execute(() -> {
            check();
        });
    });

    void check() {
        P p = pos.remove(0);
        D d = dos.remove(0);

        //执行对账操作
        //差异写入差异库
    }

    /**
     * 线程t1负责查询订单,当查出一条时,调用cyclicBarrier.await()来将计数器减1,同时等待计数器变为0;
     * 线程t2负责查询运单,当查出一条时,调用cyclicBarrier.await()来将计数器减1,同时等待计数器变为0;
     * 当t1,t2都调用cyclicBarrier.await()的时候,计数器会减为0,此时t1和t2就可以执行下一条语句了,同时会调用CyclicBarrier的回调函数.
     * CyclicBarrier有自动重置的功能,当减到0的时候,会自动重置设置的初始值.
     */
    void checkAll() {
        //循环查询订单库
        Thread t1 = new Thread(() -> {
            //存在未对账订单
            while (true) {
                //查询订单库 getPOrder()
                pos.add(null);
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();

        //循环查询运单库
        Thread t2 = new Thread(() -> {
            //存在未对账订单
            while (true) {
                //查询运单库 getDOrder()
                dos.add(null);
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        });
        t2.start();
    }

}
