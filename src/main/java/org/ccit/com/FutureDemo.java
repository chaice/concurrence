package org.ccit.com;

import java.util.concurrent.*;

public class FutureDemo {

    /**
     * Future 相关方法
     * cancel();取消任务
     * isCancelled();判断任务是否已取消
     * isDone();判断任务是否已结束
     * get();获得任务执行的结果 阻塞式,如果被调用的时候,任务还没有执行完,那么调用get()方法的线程会被阻塞
     * get(long,TimeUnit);获得任务执行的结果,支持超时机制
     * ThreadPoolExecutor 三种 submit()
     * 1.Future<?> submit(Runnable task) 提交Runnable任务.这个方法的参数是一个Runnable接口,Runnable接口的run()是没有返回值的.这个方法返回的Future仅可以用来断言任务已经结束了.类似Thread.join()
     * 2.<T> Future<T> submit(Runnable task, T result) 提交Runnable任务及结果引用.
     * 3.<T> Future<T> submit(Callable<T> task) 提交Callable任务.call()是有返回值的,返回的Future对象通过调用其get()获取任务的执行结果
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        checkIsAsync();
    }

    //利用FutureTask很容易获取子线程的执行结果
    static void futureTaskDemo() throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask<>(() -> 1 + 2);
        ExecutorService es = Executors.newCachedThreadPool();
        es.submit(futureTask);
        System.out.println(futureTask.get());
    }

    static void boilingWaterTea() throws ExecutionException, InterruptedException {
        FutureTask<String> ft2 = new FutureTask<>(new T2Task());
        FutureTask<String> ft1 = new FutureTask<>(new T1Task(ft2));

        Thread t1 = new Thread(ft1);
        t1.start();

        Thread t2 = new Thread(ft2);
        t2.start();

        System.out.println(ft1.get());
    }

    static void checkIsAsync() {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        Future<Integer> f1 = executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        });

        Future<Integer> f2 = executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 5;
        });

        Future<Integer> f3 = executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10;
        });

        try {
            System.out.println(f1.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        try {
            System.out.println(f2.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        try {
            System.out.println(f3.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

//需要执行洗水壶,烧开水,泡茶
class T1Task implements Callable<String> {

    FutureTask<String> ft2;

    T1Task(FutureTask<String> ft2) {
        this.ft2 = ft2;
    }

    @Override
    public String call() throws Exception {
        System.out.println("T1:洗水壶");
        TimeUnit.SECONDS.sleep(1);

        System.out.println("T1:烧开水");
        TimeUnit.SECONDS.sleep(2);

        //拿茶叶
        String tf = ft2.get();
        System.out.println("T1:拿到茶叶:" + tf);

        System.out.println("T1:泡茶");

        return "上茶" + tf;
    }
}

//洗茶壶,洗茶杯,拿茶叶
class T2Task implements Callable<String> {

    @Override
    public String call() throws Exception {
        System.out.println("T2:洗茶壶");
        TimeUnit.SECONDS.sleep(1);

        System.out.println("T2:洗茶杯");
        TimeUnit.SECONDS.sleep(2);

        System.out.println("T2:拿茶叶");
        TimeUnit.SECONDS.sleep(1);
        return "龙井";
    }
}
