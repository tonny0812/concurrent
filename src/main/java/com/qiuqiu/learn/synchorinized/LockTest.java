package com.qiuqiu.learn.synchorinized;

import java.util.concurrent.locks.ReentrantLock;

public class LockTest {
    private ReentrantLock lock = new ReentrantLock();//乐观锁

    public static void main(String[] argv) {
        LockTest lockTest = new LockTest();
        new TestThread5(lockTest).start();
        new TestThread5(lockTest).start();
    }

    // 循环方法
    public void loop() {
        System.out.println("thread name：" + Thread.currentThread().getName());
        lock.lock(); // 加锁
        System.out.println("thread name：" + Thread.currentThread().getName()
                + " 开始执行循环");
        for (int i = 0; i < 10; i++) {
            System.out.println("thread name："
                    + Thread.currentThread().getName() + " i=" + i);
        }
        System.out.println("thread name：" + Thread.currentThread().getName()
                + " 执行循环结束");

        lock.unlock();//执行完成释放锁

    }
}
// 测试线程
class TestThread5 extends Thread {
    private LockTest lockTest;

    public TestThread5(LockTest lockTest) {
        super();
        this.lockTest = lockTest;
    }

    public void run() {
        lockTest.loop();
    }
}
