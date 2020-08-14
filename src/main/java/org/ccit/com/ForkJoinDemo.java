package org.ccit.com;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinDemo {

    /**
     * Fork对应的是任务模型里的任务分解,Join对应的是结果合并
     * Fork/Join并行框架主要解决的是分治任务.分治的核心是分而治之:将一个大的任务拆分成小的任务去解决,然后再把子任务的处理结果聚合起来从而得到最终结果
     * 核心组件是ForkJoinPool.ForkJoinPool支持任务窃取机制,能够让所有线程的工作量基本均衡.
     */
    public static void main(String[] args) {
//        calcWordCount();
        fibonacciCalc();
    }

    static void fibonacciCalc() {
        ForkJoinPool forkJoinPool = new ForkJoinPool(3);
        Fibonacci fibonacci = new Fibonacci(50);
        Integer result = forkJoinPool.invoke(fibonacci);
        System.out.println(result);
    }

    static void calcWordCount() {
        String[] fc = {"hello world", "hello me", "hello fork", "hello join", "fork join in world"};
        ForkJoinPool forkJoinPool = new ForkJoinPool(3);
        MR mr = new MR(fc, 0, fc.length);
        Map<String, Long> result = forkJoinPool.invoke(mr);
        result.forEach((k, v) -> {
            System.out.println(k + ":" + v);
        });
    }
}

class MR extends RecursiveTask<Map<String, Long>> {

    private String[] fc;

    private int start, end;

    public MR(String[] fc, int start, int end) {
        this.fc = fc;
        this.start = start;
        this.end = end;
    }

    @Override
    public Map<String, Long> compute() {
        if (end - start == 1) {
            return calc(fc[start]);
        } else {
            int mid = (start + end) / 2;
            MR f1 = new MR(fc, start, mid);
            f1.fork();
            MR f2 = new MR(fc, mid, end);
            return merge(f2.compute(), f1.join());
        }
    }

    private Map<String, Long> merge(Map<String, Long> r1, Map<String, Long> r2) {
        Map<String, Long> result = new HashMap<>();
        result.putAll(r1);

        r2.forEach((k, v) -> {
            Long c = result.get(k);
            if (c != null) {
                result.put(k, v + c);
            } else {
                result.put(k, v);
            }
        });

        return result;
    }

    //统计单词数量
    private Map<String, Long> calc(String line) {
        Map<String, Long> result = new HashMap<>();

        String[] words = line.split("\\s+");
        for (String w : words) {
            if (result.get(w) != null) {
                result.put(w, result.get(w) + 1);
            } else {
                result.put(w, 1L);
            }
        }
        return result;
    }
}

//斐波那契数列
class Fibonacci extends RecursiveTask<Integer> {

    final int n;

    Fibonacci(int n) {
        this.n = n;
    }

    @Override
    public Integer compute() {
        if (n <= 1) {
            return n;
        }
        Fibonacci f1 = new Fibonacci(n - 1);
        f1.fork();
        Fibonacci f2 = new Fibonacci(n - 2);

        return f2.compute() + f1.join();
    }
}
