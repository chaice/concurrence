package org.ccit.com;

import java.util.ArrayList;
import java.util.List;

public class Deadlock {

    /**
     * 发生死锁条件:
     * 1.互斥，共享资源X和Y只能被一个线程占用
     * 2.占有且等待，线程T1已经取得共享资源X，在等待共享资源Y时，不释放共享资源X
     * 解决：可以一次性申请所有的资源，这样就不存在等待了
     * 3.不可抢占，其他线程不能强行抢占线程T1占有的资源
     * 解决：占用部分资源的线程进一步申请其他资源时，如果申请不到，可以主动释放它占有的资源。
     * 4.循环等待，线程T1等待线程T2占有的资源，线程T2等待线程T1占有的资源，就是循环等待
     * 解决：可以靠按顺序申请来预防。所谓按序申请，是指资源是有线性顺序的，申请的时候可以先申请资源号小的，再申请资源号大的，这样线性化后自然就不存在循环了
     */
    public static void main(String[] args) {

    }

}

class Allocator {

    private List<Object> als = new ArrayList<>();

    synchronized boolean apply(Object from, Object to) {
        if (als.contains(from) || als.contains(to)) {
            return false;
        } else {
            als.add(from);
            als.add(to);
        }
        return true;
    }

    synchronized void free(Object from, Object to) {
        als.remove(from);
        als.remove(to);
    }
}

class Account {
    private Allocator allocator;

    private int balance;

    void transfer(Account target, int amt) {
        //申请转出和转入账户，直到成功
        while (!allocator.apply(this, target)) ;
        try {
            synchronized (this) {
                synchronized (target) {
                    if (this.balance > amt) {
                        this.balance -= amt;
                        target.balance += amt;
                    }
                }
            }
        } finally {
            allocator.free(this, target);
        }

    }
}

class AccountLoop {

    private int id;

    private int balance;

    void transfer(AccountLoop target, int amt) {

        AccountLoop left = this;

        AccountLoop right = target;

        if (this.id > right.id) {
            left = target;
            right = this;
        }

        //锁定序号小的账户
        synchronized (left) {
            //锁定序号大的账户
            synchronized (right) {
                if (this.balance > amt) {
                    this.balance -= amt;
                    target.balance += amt;
                }
            }
        }
    }
}
