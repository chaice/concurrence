package org.ccit.com;

import java.util.concurrent.atomic.AtomicLong;

public class SafeMW {

    private final AtomicLong upper = new AtomicLong();

    private final AtomicLong lower = new AtomicLong();

    void setUpper(long v) {
        if (v < lower.get()) {
            throw new IllegalArgumentException();
        }
        upper.set(v);
    }

    void setLower(long v) {
        if (v > upper.get()) {
            throw new IllegalArgumentException();
        }
        lower.set(v);
    }

    public static void main(String[] args) {
        SafeMW safeMW = new SafeMW();

        Thread t1 = new Thread();
        t1.start();
    }

}
