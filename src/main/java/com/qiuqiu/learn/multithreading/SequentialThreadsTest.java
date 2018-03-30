package com.qiuqiu.learn.multithreading;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 指定线程执行顺序的方式：
 * 1、通过共享对象锁+可见变量实现
 * 2、通过主线程Join()
 * 3、通过线程执行时Join()
 */
public class SequentialThreadsTest {
    public static void main(String[] args) throws InterruptedException {
//        ThreadOrderTest.test();
//        ThreadOrderTest.test2();
//        ThreadOrderTest.test3();
//        ThreadOrderTest.test4();
        ThreadOrderTest.test5();
    }
}

/**
 * 指定线程执行顺序：通过synchronized共享对象锁加上volatile可见变量来实现
 *
 *
 * 可以看到线程的启动按顺序执行了。共享对象锁，可以保证每个方法只能同时有一个线程进入，配合wait和notifyAll方法，可以启动或者唤醒线程
 */
class ThreadOrder {
    private volatile int orderNum = 1;

    public synchronized void methodA() {
        try {
            while(orderNum != 1) {
                wait();
            }
            System.out.println(Thread.currentThread().getName());
            for(int i=0;i<2;i++) {
                System.out.println("\tAAAAAA");
            }
            orderNum = 2;
            notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void methodB() {
        try {
            while(orderNum != 2) {
                wait();
            }
            System.out.println(Thread.currentThread().getName());
            for(int i=0;i<2;i++) {
                System.out.println("\tBBBBB");
            }
            orderNum = 3;
            notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void methodC() {
        try {
            while(orderNum != 3) {
                wait();
            }
            System.out.println(Thread.currentThread().getName());
            for(int i=0;i<2;i++) {
                System.out.println("\tCCCCC");
            }
            orderNum = 1;
            notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


class ThreadOrderTest {

    /**
     * 指定线程执行顺序：通过共享对象锁加上可见变量来实现
     */
    public static void test() {
        ThreadOrder threadOrder = new ThreadOrder();
        for(int i=0;i<10;i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    threadOrder.methodA();
                }
            }, "ThreadA-"+i).start();
        }

        for(int i=0;i<10;i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    threadOrder.methodB();
                }
            }, "ThreadB-"+i).start();
        }

        for(int i=0;i<10;i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    threadOrder.methodC();
                }
            }, "ThreadC-"+i).start();
        }
    }


    /**
     * 通过主线程join()
     * @throws InterruptedException
     */
    public static void test2() throws InterruptedException {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("AAA");
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("BBB");
            }
        });
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("CCC");
            }
        });
        thread1.start();
        thread1.join();
        thread2.start();
        thread2.join();
        thread3.start();
        thread3.join();
    }

    /**
     * 通过线程执行时Join()
     */
    public static void test3() {
        class T1 extends Thread {
            @Override
            public void run() {
                Random random = new Random();
                try {
                    Thread.sleep(random.nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("join T1");
            }
        }
        class T2 extends Thread {
            private Thread thread;
            public T2(Thread thread) {
                this.thread = thread;
            }
            @Override
            public void run() {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("join T2");
            }
        }

        class T3 extends Thread {
            private Thread thread;
            public T3(Thread thread) {
                this.thread = thread;
            }
            @Override
            public void run() {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("join T3");
            }
        }

        T1 t1 = new T1();
        T2 t2 = new T2(t1);
        T3 t3 = new T3(t2);
        t1.start();
        t2.start();
        t3.start();
    }

    /**
     * 信号量（Semaphore）
     *
     * 　Semaphore常用的场景：
     *      高性能的互斥锁实现（本题有涉及）
     *      限制访问公共资源的线程个数，如只有5个资源，那么同时执行的线程就只能是5个
     *      控制多线程执行流程控制，如：主线程等待所有异步线程执行完毕后再执行、或者多个异步线程的执行顺序（类似本题目）
     */
    public static void test4() {
        class SemaphoreTest {
            private final Semaphore semaphoreA = new Semaphore(1);
            private final Semaphore semaphoreB = new Semaphore(1);
            private final Semaphore semaphoreC = new Semaphore(1);

            public void start() throws InterruptedException {

                //ABC线程启动之前 获取SemaphoreB的1个资源，保证线程A最先执行
                semaphoreB.acquire();
                //ABC线程启动之前 获取SemaphoreC的1个资源，保证线程A最先执行
                semaphoreC.acquire();
                for(int i=0;i<10;i++) {
                    new Thread(new Runnable(){
                        @Override
                        public void run() {
                            try {
                                semaphoreA.acquire();
                                System.out.print(Thread.currentThread().getName());
                                //之前说的特性：可以在ThreadA释放ThreadB的Semaphore资源， 下同
                                semaphoreB.release();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }, "ThreadA-"+i).start();
                }

                for(int i=0;i<10;i++) {
                    new Thread(new Runnable(){
                        @Override
                        public void run() {
                            try {
                                semaphoreB.acquire();
                                System.out.print(Thread.currentThread().getName());
                                //之前说的特性：可以在ThreadA释放ThreadB的Semaphore资源， 下同
                                semaphoreC.release();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }, "ThreadB-"+i).start();
                }

                for(int i=0;i<10;i++) {
                    new Thread(new Runnable(){
                        @Override
                        public void run() {
                            try {
                                semaphoreC.acquire();
                                System.out.print(Thread.currentThread().getName());
                                System.out.println("");
                                //之前说的特性：可以在ThreadA释放ThreadB的Semaphore资源， 下同
                                semaphoreA.release();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }, "ThreadC-"+i).start();
                }
            }
        }
        SemaphoreTest semaphoreTest = new SemaphoreTest();
        try {
            semaphoreTest.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用ReentrantLock来解决, 还有个state整数用来判断轮到谁执行了
     */
    public static void test5() {
        ABC.test();
    }

    static class ABC {
        private static Lock lock = new ReentrantLock();//通过JDK5中的锁来保证线程的访问的互斥
        private static int state = 0;
        static class ThreadA extends Thread {
            @Override
            public void run() {
                for (int i = 0; i < 10;) {
                    lock.lock();
                    if (state % 3 == 0) {
                        System.out.print("A");
                        state++;
                        i++;
                    }
                    lock.unlock();
                }
            }
        }

        static class ThreadB extends Thread {
            @Override
            public void run() {
                for (int i = 0; i < 10;) {
                    lock.lock();
                    if (state % 3 == 1) {
                        System.out.print("B");
                        state++;
                        i++;
                    }
                    lock.unlock();
                }
            }
        }

        static class ThreadC extends Thread {
            @Override
            public void run() {
                for (int i = 0; i < 10;) {
                    lock.lock();
                    if (state % 3 == 2) {
                        System.out.print("C");
                        System.out.println("");
                        state++;
                        i++;
                    }
                    lock.unlock();
                }
            }
        }

        public static void test() {
            new ThreadA().start();
            new ThreadB().start();
            new ThreadC().start();
        }
    }
}
