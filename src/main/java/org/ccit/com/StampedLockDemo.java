package org.ccit.com;

import java.util.concurrent.locks.StampedLock;

public class StampedLockDemo {

    public static void main(String[] args) {

    }

    final StampedLock stampedLock = new StampedLock();

    void get() {
        //获取/释放悲观读锁
        long stamp = stampedLock.readLock();
        try {

        } finally {
            stampedLock.unlockRead(stamp);
        }
    }

    void put() {
        long stamp = stampedLock.writeLock();
        try {

        } finally {
            stampedLock.unlockWrite(stamp);
        }
    }
}

class Point {
    private int x, y;

    final StampedLock stampedLock = new StampedLock();

    double distanceFromOrigin() {

        //获取乐观读锁
        long stamp = stampedLock.tryOptimisticRead();

        //读入局部变量 数据获取过程中可能被修改
        int curX = x;
        int curY = y;
        //判断读操作期间,是否存在写操作,如果存在则 validate() 返回false
        if (!stampedLock.validate(stamp)) {
            //升级为悲观锁
            stamp = stampedLock.readLock();
            try {
                curX = x;
                curY = y;
            } finally {
                //释放悲观锁
                stampedLock.unlock(stamp);
            }
        }
        return Math.sqrt(curX * curX + curY * curY);
    }
}
