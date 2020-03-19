package org.ccit;

public class MemoryModel {

    /*
    1.volatile关键字 ：禁用cpu缓存，必须从内存中读取或者写入
    2.Happens-Before(前面一个操作的结果对后续操作是可见的)六项规则
        ①.程序的顺序性原则：程序前面对某个变量的修改一定是对后续操作可见的；
        ②.volatile变量规则：一个volatile变量的写操作对这个volatile变量的读操作可见；
        ③.传递性： A Happens-Before B，且B Happens-Before C，那么A Happens-Before C；
        ④.管程中的锁规则：指对一个锁的解锁Happens-Before于后续对这个锁的加锁，管程是一种通用的同步原语，在Java中指的就是synchronized，synchronized是Java里对管程的实现；
        ⑤.线程start()规则：指主线程A启动子线程B,子线程B能够看到主线程在启动子线程B前的操作；
        ⑥.
     */

    public static void main(String[] args) {

    }

    class VolatileExample {
        //线程A执行writer();线程B执行reader();线程B得到的x是多少？

        int x = 0;
        volatile boolean y = false;

        public void writer() {
            x = 42;
            y = true;

            synchronized (this){//此处自动加锁

            }//此处自动解锁
        }

        public int reader() {
            if (y) {
                return x;
            }
            return x;
        }

    }
}
