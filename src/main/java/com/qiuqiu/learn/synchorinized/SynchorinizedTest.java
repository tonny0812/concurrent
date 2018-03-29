package com.qiuqiu.learn.synchorinized;

/**
 * 修饰普通对象即synchronized(obj)，如string实例、其它类的实例
 */
public class SynchorinizedTest {
    public static void main(String[] argv) {
        new TestThread("12345678asdf").start();
        new TestThread("12345678asdf").start();
    }
    public void loop(String random) {
        System.out.println("thread name：" + Thread.currentThread().getName()
                + " 传入的random:" + random);
        synchronized (random) {
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

    public void loop2() {
        System.out.println("thread name：" + Thread.currentThread().getName());
        synchronized (SynchorinizedTest.class) {
            System.out.println(" thread name："
                    + Thread.currentThread().getName() + " 开始执行循环");
            for (int i = 10; i < 20; i++) {
                System.out.println("thread name："
                        + Thread.currentThread().getName() + " i=" + i);
            }
            System.out.println("thread name："
                    + Thread.currentThread().getName() + " 执行循环结束");

        }

    }

}
class TestThread extends Thread {
    private String name;

    public TestThread(String name) {
        super();
        this.name = name;
    }

    public void run() {
        SynchorinizedTest syntest = new SynchorinizedTest();
        syntest.loop(name);
    }
}
