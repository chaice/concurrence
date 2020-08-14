package org.ccit.com;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureDemo {

    /**
     * CompletionStage 接口
     * 1.描述串行关系 主要是thenApply() 支持参数有返回值 ,thenAccept() 支持参数无返回值,thenRun(),thenCompose()
     * 2.描述汇聚And(And指的是所有依赖的任务都完成后才开始执行当前任务) 主要是thenCombine(),thenAcceptBoth(),runAfterBoth()
     * 3.描述汇聚Or(Or指的是依赖的任务只要完成一个就可以执行当前任务) 主要是applyToEither(),acceptEither(),runAfterEither()
     * 4.异常处理 主要是exceptionally()相当于catch,whenComplete()相当于finally无返回值,handle()相当于finally有返回值.
     */
    public static void main(String[] args) {
        handleException();
    }

    static void boilingWaterTea() {
        //runAsync();无返回值
        CompletableFuture<Void> f1 = CompletableFuture.runAsync(() -> {

            System.out.println("T1:洗水壶");
            sleep(1, TimeUnit.SECONDS);

            System.out.println("T1:烧开水");
            sleep(15, TimeUnit.SECONDS);
        });

        //supplyAsync();有返回值
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {

            System.out.println("T2:洗茶壶...");
            sleep(1, TimeUnit.SECONDS);

            System.out.println("T2:洗茶杯...");
            sleep(2, TimeUnit.SECONDS);

            System.out.println("T2:拿茶叶...");
            sleep(1, TimeUnit.SECONDS);

            return "龙井";
        });

        CompletableFuture<String> f3 = f1.thenCombine(f2, (__, tf) -> {
            System.out.println("T1:拿到茶叶:" + tf);
            System.out.println("T1:泡茶");

            return "上茶" + tf;
        });

        System.out.println(f3.join());
    }

    static void sleep(int t, TimeUnit u) {
        try {
            u.sleep(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //串行使用示例
    static void serialDemo() {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello World").thenApply(s -> s + " !").thenApply(String::toUpperCase);
        System.out.println(completableFuture.join());
    }

    //汇聚Or使用示例
    static void convergeOrDemo() {
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
            return String.valueOf(2);
        });

        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
            return String.valueOf(5);
        });

        CompletableFuture<String> f3 = f1.applyToEither(f2, s -> s);
        System.out.println(f3.join());
    }

    //异常处理
    static void handleException() {
        CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> (7 / 0)).thenApply(r -> r * 5).exceptionally(e -> 0).handle((s,th)->{
            System.out.println(s);
            return 123;
        });
        System.out.println(f1.join());
    }

}
