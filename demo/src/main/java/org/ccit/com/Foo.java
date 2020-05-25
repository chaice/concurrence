package org.ccit.com;

public class Foo {

    private long count = 0;

    private synchronized void add10K() {
        int index = 0;
        while (index++ < 10000) {
            count += 1;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        VolatileExample volatileExample = new VolatileExample();
        Thread th1 = new Thread(() -> {
            volatileExample.writer();
        });

        Thread th2 = new Thread(() -> {
            volatileExample.reader();
        });

        th1.start();
        th2.start();
    }

    public long getCount() {
        return count;
    }
}

class VolatileExample {

    int x = 0;

    volatile boolean y = false;

    public void writer() {
        x = 42;
        y = true;
    }

    public void reader() {
        if (y) {
            System.out.println(x);
        }
    }
}

class Singleton {

    static volatile Singleton instance;

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if(instance == null){
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
