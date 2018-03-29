package com.qiuqiu.learn.synchorinized;

/**
 * 混合使用修饰方法和代码块结合
 */
public class SynchorizedTest3 {
    public static void main(String[] argv) {
        SynchorizedTest3 syn1=new SynchorizedTest3();
        SynchorizedTest3 syn2=new SynchorizedTest3();
        new TestThread3(syn1).start();
        new TestThread4(syn1).start();
        new TestThread4(syn2).start();
    }
    //循环方法
    public void loop() {
        System.out.println("thread name：" + Thread.currentThread().getName());
        synchronized (this) {
            System.out.println("thread name："
                    + Thread.currentThread().getName() + " 开始执行循环");
            for (int i = 0; i < 10; i++) {
                System.out.println("thread name："
                        + Thread.currentThread().getName() + " i=" + i);
            }
            System.out.println("thread name："
                    + Thread.currentThread().getName() + " 执行循环结束");

        }

    }
    //循环方法1
    public synchronized void loop1() {
        System.out.println("thread name：" + Thread.currentThread().getName());

        System.out.println("thread name：" + Thread.currentThread().getName()
                + " 开始执行循环");
        for (int i = 0; i < 10; i++) {
            System.out.println("thread name："
                    + Thread.currentThread().getName() + " i=" + i);
        }
        System.out.println("thread name：" + Thread.currentThread().getName()
                + " 执行循环结束");

    }

}
//测试线程
class TestThread3 extends Thread {
    private SynchorizedTest3 syn;

    public TestThread3(SynchorizedTest3 syn) {
        super();
        this.syn = syn;
    }

    public void run() {
        syn.loop();
    }

}
//测试线程1
class TestThread4 extends Thread {
    private SynchorizedTest3 syn;

    public TestThread4(SynchorizedTest3 syn) {
        super();
        this.syn = syn;
    }

    public void run() {

        syn.loop1();
    }
}
