package org.ccit.com;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.locks.LockSupport;

public class ThreadLifecycle1 {

    /**
     * 通用的线程生命周期：五状态（初始状态，可运行状态，运行状态，休眠状态，终止状态）
     *   1.初始状态：指的是线程已经被创建，但是还不允许分配CPU执行。这里的已创建
     *   2.可运行状态：指的是线程可以分配CPU执行。在这种状态下，真正的操作系统线程已经被成功创建，所以可以分配CPU
     *   3.当有空闲的CPU时，操作系统会将其分配给一个处于可运行状态的线程，被分配到CPU的线程的状态就转换成了运行状态
     *   4.运行状态的线程如果调用一个阻塞的API（例如以阻塞方式读文件）或者等待某个事件（例如条件变量），那么线程的状态就会转换到休眠状态，同时释放CPU使用权。
     *     当等待的事件出现了，线程就会从休眠状态转换到可运行状态。
     *   5.线程执行完或者出现异常就会进入终止状态，终止状态的线程不会切换到其他任何状态，进入终止状态意味着线程的生命周期结束了。
     *
     * Java中线程的生命周期：NEW（初始化状态），RUNNABLE（可运行/运行状态），BLOCKED（阻塞状态），WAITING（无限时等待），TIME_WAITING（有限时等待），TERMINATED（终止状态）
     *   只要Java线程处于这三种状态之一，那么这个线程就永远没有CPU的使用权
     *
     * stop方法会真的杀死线程，不给线程喘息的机会，如果线程持有ReentrantLock锁被stop的线程并不会调用unlock()方法，那么其他线程就再也没有机会获得ReentrantLock锁，类似的方法还有suspend()，resume().
     * interrupt()方法仅仅是通知线程，线程有机会执行后续操作，同时也可以无视这个通知。
     */
    public static void main(String[] args) {
        LockSupport.park();
    }

}
