package org.ccit.com;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {

    /**
     * 读写锁的共同特点:
     * 1.允许多个线程同时读共享变量
     * 2.只允许一个线程写共享变量
     * 3.如果一个线程正在写共享变量,此时禁止读共享变量
     *
     * 写锁支持条件变量 Condition,读锁调用newCondition()会抛出UnsupportedOperationException异常
     */
    public static void main(String[] args) {

    }

}

class Cache<K, V> {

    final Map<K, V> m = new HashMap<>();

    volatile boolean cacheValid;

    Object data;

    final ReadWriteLock rwl = new ReentrantReadWriteLock();

    final Lock r = rwl.readLock();

    final Lock w = rwl.writeLock();

    //读缓存,不存在数据库查找
    V get(K k) {
        V v = null;
        r.lock();
        try {
            v = m.get(k);
        } finally {
            r.unlock();
        }

        if (v != null) {
            return v;
        }

        //缓存中不存在,查询数据库
        w.lock();
        try {
            //再次验证,其他线程可能已经查询过
            v = m.get(k);
            if (v == null) {
                //查询数据库
                //v = ***;
                m.put(k, v);
            }
        } finally {
            w.unlock();
        }

        return v;
    }

    //写缓存
    V put(K k, V v) {
        w.lock();
        try {
            return m.put(k, v);
        } finally {
            w.unlock();
        }
    }

    void processCachedData() {
        //获取读锁
        r.lock();
        if (!cacheValid) {
            //释放读锁,不允许锁的升级
            r.unlock();
            w.lock();
            try {
                if (!cacheValid) {
                    //data = XX;
                    cacheValid = true;
                }
                //释放写锁前,降级为读锁
                r.lock();
            } finally {
                w.unlock();
            }
        }

        try {
            //仍然持有读锁
            //use(data);
        } finally {
            r.unlock();
        }
    }

}
