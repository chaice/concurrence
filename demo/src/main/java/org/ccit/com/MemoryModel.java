package org.ccit.com;

public class MemoryModel {

    /*
    1.volatile关键字 ：禁用cpu缓存，必须从内存中读取或者写入
    2.Happens-Before(前面一个操作的结果对后续操作是可见的)六项规则 A事件是导致B事件的起因，那么A事件一定是先于B事件发生的。约束了编译器的优化行为，虽然允许编译器优化，但是要求编译器优化后一定遵守规则
        ①.程序的顺序性原则：程序前面对某个变量的修改一定是对后续操作可见的；
        ②.volatile变量规则：一个volatile变量的写操作对这个volatile变量的读操作可见；
        ③.传递性： A Happens-Before B，且B Happens-Before C，那么A Happens-Before C；
        ④.管程中的锁规则：指对一个锁的解锁Happens-Before于后续对这个锁的加锁，管程是一种通用的同步原语，在Java中指的就是synchronized，synchronized是Java里对管程的实现；
        ⑤.线程start()规则：指主线程A启动子线程B,子线程B能够看到主线程在启动子线程B前的操作；
        ⑥.线程join规则：指主线程A等待子线程B完成（主线程A通过调用子线程B的join()方法实现），当子线程B完成后（主线程）
    3.final关键字：final修饰变量时，初衷是告诉编译期：这个变量生而不变可以可劲优化。
    4.思考题：一个共享变量abc，在一个线程里设置了abc的值=3，有哪些办法可以让其他线程能够看到abc=3。
    5.思考题：在32位的机器上对long型进行加减操作存在并发隐患。long类型是64位，所以在32位机器上对long型的操作通常需要多条指令组合出来，无法保证原子性。
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
