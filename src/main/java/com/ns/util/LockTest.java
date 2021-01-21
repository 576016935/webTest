package com.ns.util;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest {

    public static void main(String[] args) throws InterruptedException {
        //MyThread myThread= new MyThread();
        MyRunnable myRunnable = new MyRunnable();

        new Thread(myRunnable,"myThread线程1").start();
        new Thread(myRunnable,"myThread线程2").start();
        new Thread(myRunnable,"myThread线程3").start();
        new Thread(myRunnable,"myThread线程4").start();

        new Thread(myRunnable,"myRunnable线程5").start();
        new Thread(myRunnable,"myRunnable线程6").start();
        new Thread(myRunnable,"myRunnable线程7").start();
        new Thread(myRunnable,"myRunnable线程8").start();
    }
}
class MyThread extends Thread{
    private Integer sum=10000;
    private Integer num=0;
    Lock lock = new ReentrantLock();

    @Override
    public void run() {
        lock.lock();
        while (sum>=1){
            if (this.sum >= 1) {
                System.out.println(Thread.currentThread().getName() + "正在卖第" + this.sum-- + "张票");
                num++;
            }
        }
        lock.unlock();
        System.out.println("myThread运行了"+num+"次");
    }
}

class MyRunnable implements Runnable{

    private Integer sum=10000;
    private Integer num=0;
    Lock lock = new ReentrantLock();

    @Override
    public void run() {
        while (sum>=1){
            lock.lock();
            try {
                if(this.sum>=1){
                    System.out.println(Thread.currentThread().getName()+"正在卖第"+this.sum -- +"张票");
                    num++;
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
        System.out.println("MyRunnable运行了"+num+"次");
    }
}