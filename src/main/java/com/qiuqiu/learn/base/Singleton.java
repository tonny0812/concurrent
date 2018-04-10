package com.qiuqiu.learn.base;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Singleton {
    private volatile static Singleton instance;
    private Singleton() {}
    public static Singleton getInstance() {
        if(null == instance) {
            synchronized(Singleton.class) {
                if(null == instance) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

    public void show() {
        System.out.println("Hello Word!!");
    }

    public static void main(String[] args) {
        ExecutorService e = Executors.newFixedThreadPool(10);

        for(int index=0;index<20;index++) {
            e.execute(new Runnable() {
                @Override
                public void run() {
                    Singleton singleton = Singleton.getInstance();
                    System.out.println(Thread.currentThread().getName() + "->" + singleton.toString());
                }
            });
        }
        e.shutdown();
    }
}