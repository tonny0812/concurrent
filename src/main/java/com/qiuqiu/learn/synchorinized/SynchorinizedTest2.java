package com.qiuqiu.learn.synchorinized;

/**
 * 修饰类的字节码即synchronized(obj)中obj为某个类的class
 */
public class SynchorinizedTest2 {
    public static void main(String[] argv) {
        SynchorinizedTest2 syn1=new SynchorinizedTest2();
        SynchorinizedTest2 syn2=new SynchorinizedTest2();
        new TestThread2(syn1).start();
        new TestThread2(syn2).start();
    }
    public void loop() {
        System.out.println("thread name：" + Thread.currentThread().getName());
        synchronized (SynchorinizedTest2.class) {
            System.out.println(" thread name："
                    + Thread.currentThread().getName() + " 开始执行循环");
            for (int i = 0; i < 10; i++) {
                System.out.println("thread name："
                        + Thread.currentThread().getName() + " i=" + i);
            }
            System.out.println("thread name："
                    + Thread.currentThread().getName() + " 执行循环结束");

        }

    }
}
class TestThread2 extends Thread {
    private SynchorinizedTest2 syn;

    public TestThread2(SynchorinizedTest2 syn) {
        super();
        this.syn = syn;
    }

    public void run() {
        syn.loop();
    }

}
