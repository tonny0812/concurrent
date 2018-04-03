package com.qiuqiu.learn.multithreading;

public class ThreadSleepWait {

    public static void main(String[] args) throws Exception {
        for(int i=0;i<10;i++) {
            ThreadTest test = new ThreadTest();
            new Thread(test,"Thread Sleep " + i).start();
            test.sleepMethod();
        }
        for(int i=0;i<10;i++) {
            ThreadTest test = new ThreadTest();
            new Thread(test,"Thread Wait " + i).start();
            test.waitMethod();
        }
    }
}

class ThreadTest implements Runnable {

    int number = 10;

    public void addNumberMethod() throws Exception {
        synchronized (this) {
            number += 100;
            System.out.println("-----------"+Thread.currentThread().getName() +"---------------------");
            System.out.println(number);
        }
    }

    public void sleepMethod() throws Exception {
        synchronized (this) {
            /**
             * (休息2S,阻塞线程)
             * 以验证当前线程对象的机锁被占用时,
             * 是否被可以访问其他同步代码块
             */
            Thread.sleep(2000);
            //this.wait(2000);
            number *= 200;
        }
    }

    public void waitMethod() throws Exception {
        synchronized (this) {
            /**
             * (休息2S,阻塞线程)
             * 以验证当前线程对象的机锁被占用时,
             * 是否被可以访问其他同步代码块
             */
//                Thread.sleep(2000);
            this.wait(2000);
            number *= 200;
        }
    }
    @Override
    public void run() {
        try {
            addNumberMethod();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}