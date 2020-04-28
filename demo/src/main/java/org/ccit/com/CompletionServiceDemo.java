package org.ccit.com;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CompletionServiceDemo {

    public static void main(String[] args) {
        forkingMode();
    }

    static void handleDemo() {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        CompletionService<Integer> completionService = new ExecutorCompletionService<>(executorService);

        completionService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        });

        completionService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 5;
        });

        completionService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10;
        });

        for (int i = 0; i < 3; i++) {
            try {
                System.out.println(completionService.take().get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    //实现 dubbo Forking集群模式(这种集群模式下,支持并行调用多个查询服务,只要有一个成功返回结果,整个服务就可以返回了)
    static void forkingMode() {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        CompletionService<Integer> es = new ExecutorCompletionService(executorService);

        List<Future<Integer>> futures = new ArrayList<>();

        futures.add(
                es.submit(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return 1;
                })
        );

        futures.add(
                es.submit(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return 5;
                })
        );

        futures.add(
                es.submit(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return 4;
                })
        );

        for (int i = 0; i < 3; i++) {
            try {
                Integer r = es.take().get();
                if (r != null) {
                    System.out.println(r);
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        for (Future<Integer> future : futures) {
            future.cancel(true);
        }
    }
}
