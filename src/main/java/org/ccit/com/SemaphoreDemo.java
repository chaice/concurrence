package org.ccit.com;

import java.util.List;
import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.function.Function;

public class SemaphoreDemo {

    //限流器
    public static void main(String[] args) throws InterruptedException {
        ObjPool<Long, String> objPool = new ObjPool<>(10, 2L);
        objPool.exec(t -> {
            System.out.println(t);
            return t.toString();
        });
    }

    //计数器
    int count;

    //等待队列
    Queue queue;

    public SemaphoreDemo() {

    }

    void down() {
        this.count--;
        if (this.count < 0) {
            //将当前线程插入等待队列
            //阻塞当前线程
        }
    }

    void up() {
        this.count++;
        if (this.count <= 0) {
            //移除等待队列中的某个线程T
            //唤醒线程T
        }
    }
}

class ObjPool<T, R> {
    final List<T> pool;
    final Semaphore semaphore;

    ObjPool(int size, T t) {
        pool = new Vector<T>() {
        };
        for (int i = 0; i < size; i++) {
            pool.add(t);
        }
        semaphore = new Semaphore(size);
    }

    //利用对象池的的对象调用func
    R exec(Function<T, R> func) throws InterruptedException {
        T t = null;
        semaphore.acquire();
        try {
            t = pool.remove(0);
            return func.apply(t);
        } finally {
            pool.add(t);
            semaphore.release();
        }
    }
}
