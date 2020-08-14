package org.ccit.com;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class ExecutorAndThreadPool {

    /**
     * ThreadPoolExecutor 参数
     *   1.corePoolSize 线程池保有的最小线程数.有些项目很闲,但是也不能把人都撤了,至少要留corePoolSize个人坚守阵地
     *   2.maximumPoolSize 表示线程池创建的最大线程数
     *   3.keepAliveTime & unit 用来定义闲和忙.如果一段时间内,都没有执行任务,说明很闲,同时线程池的线程数大于 corePoolSize,那么空闲的线程就要被回收了
     *   4.workQueue 工作队列
     *   5.threadFactory 可以自定义如何创建线程,例如可以给线程指定一个有意义的名字
     *   6.handler 可以自定义任务的拒绝策略.如果线程池中所有的线程都在执行任务,并且工作队列也满了,那么此时提交任务,线程池就会拒绝接收.
     *      ThreadPoolExecutor 提供了以下4种策略
     *         ①.CallerRunsPolicy:提交任务的线程自己去执行该任务
     *         ②.AbortPolicy:默认的拒绝策略 会抛出RejectedExecutionHandler
     *         ③.DiscardPolicy:直接丢弃任务,没有任何异常抛出
     *         ④.DiscardOldestPolicy:丢弃最老的任务,把最早进入工作队列的任务丢弃,然后把新任务加入到工作队列
     * Executors 很多默认方法使用的都是无界的LinkedBlockingQueue,高负载情况下,无界队列很容易导致OOM,而OOM会导致所有请求都无法处理.使用有界队列,当任务过多时,线程池会触发执行拒绝策略,
     *
     */
    public static void main(String[] args) throws InterruptedException {
        BlockingDeque<Runnable> workQueue = new LinkedBlockingDeque<>(2);

        MyThreadPool threadPool = new MyThreadPool(10, workQueue);

        threadPool.execute(() -> {
            System.out.println("hello");
        });

    }

}

class MyThreadPool {

    BlockingDeque<Runnable> workQueue;

    List<WorkerThread> threads = new ArrayList<>();

    MyThreadPool(int poolSize, BlockingDeque<Runnable> workQueue) {
        this.workQueue = workQueue;
        for (int i = 0; i < poolSize; i++) {
            //创建工作线程
            WorkerThread workerThread = new WorkerThread();
            workerThread.start();
            threads.add(workerThread);
        }
    }

    //提交任务
    void execute(Runnable command) throws InterruptedException {
        workQueue.put(command);
    }

    //工作线程负责消费任务,并执行任务
    class WorkerThread extends Thread {
        @Override
        public void run() {
            //循环获取任务并执行
            while (true) {
                try {
                    Runnable task = workQueue.take();
                    task.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
