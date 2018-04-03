package com.qiuqiu.learn.multithreading;

public class DiedSynchronized {
    public static void main(String[] args) {
        Tellme m=new Tellme();
        Thread t1=new Thread(m,"t1");
        Thread t2=new Thread(m,"t2");
        System.out.println(Thread.currentThread().getName()+"：t1和t2线程已经启动……");
        t1.start();
        t2.start();
        try {
            Thread.sleep((long)10000);
        } catch (InterruptedException ex) { }
        if(t1.isAlive() && t2.isAlive()) {
            System.out.println(Thread.currentThread().getName()+"：10s内，t1和t2线程均没有正常结束，证明t1和t2线程死锁！");
            System.exit(0);
        }
        else
            System.out.println(Thread.currentThread().getName()+"：t1和t2线程已经结束，证明没有死锁！");
    }
}

class Tellme implements Runnable {
    private Integer _number = new Integer(100);
    public boolean _flag = true;
    public void run() {
        if(_flag) {
            _flag=false;
            synchronized(this) {
                System.out.println(Thread.currentThread().getName()+"：已经获取到this锁，正在获取Number锁……");
                try {
                    Thread.sleep((long)1000);
                } catch (InterruptedException ex) { }
                synchronized(_number) {
                    System.out.println(Thread.currentThread().getName()+"：this锁和Number锁同时获取完毕！");
                }
            }
        }else {
            synchronized(_number) {
                System.out.println(Thread.currentThread().getName()+"：已经获取到Number锁，正在获取this锁……");
                try {
                    Thread.sleep((long)1000);
                } catch (InterruptedException ex) { }
                synchronized(this) {
                    System.out.println(Thread.currentThread().getName()+"：this锁和Number锁同时获取完毕！");
                }
            }
        }
    }
}
