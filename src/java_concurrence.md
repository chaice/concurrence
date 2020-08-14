    1. Happens-Before规则
    ①.程序的顺序性规则 指在一个线程中,按照程序顺序,前面的操作Happens-Before于后续的任意操作
    ②.volatile变量规则 指对一个volatile变量的写操作,Happens-Before于后续对这个volatile变量的读操作
    ③.传递性 指如果A Happens-Before B,且B Happens-Before C.那么A Happens-Before C.
    ④.管程中的锁规则 指对一个锁的解锁Happens-Before于后续对这个锁的加锁
    ⑤.线程start()规则 指主线程启动子线程后,子线程能够看到主线程在启动子线程B前的操作
    ⑥.线程join规则 指主线程等待子线程完成,当子线程完成后,主线程能够看到子线程对共享变量的操作
    ⑦.对象终结规则 一个对象的初始化完成先行发生于它的finalize()方法的开始
    ⑧.线程中断规则 对线程interrupt()方法的调用先行发生于被中断线程的代码检测到中断时间的发生                         