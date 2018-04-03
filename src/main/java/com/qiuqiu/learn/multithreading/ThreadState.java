package com.qiuqiu.learn.multithreading;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadState {
    public static void main(String[] args) {
//        ThreadState.threadNewState();
//        ThreadState.threadRunnable();
//        ThreadState.threadBlocked();
//        ThreadState.threadWaitingObjectWait();
//        ThreadState.threadWaitingThreadJoin();
//        ThreadState.threadWaitingConditionWait();
//        ThreadState.threadTimeWaiting();
        ThreadState.threadTerminated();
    }

    private static int getCurrentThreadPid() {
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        String name = runtime.getName();
        System.out.println("当前进程的标识为："+name);
        int index = name.indexOf("@");
        if (index != -1) {
            int pid = Integer.parseInt(name.substring(0, index));
            System.out.println("当前进程的PID为："+pid);
            return pid;
        }
        return -1;
    }

    /**
     * 线程创建，NEW
     */
    public static void threadNewState() {
        Thread thread = new Thread();
        System.out.println(thread.getState());
    }

    /**
     * RUNNABLE
     *
     "Runnable - Thread" #9 prio=5 os_prio=0 tid=0x16f90800 nid=0x3b50 runnable [0x1758f000]
     java.lang.Thread.State: RUNNABLE
     at com.qiuqiu.learn.multithreading.ThreadState$1.run(ThreadState.java:41)
     at java.lang.Thread.run(Thread.java:748)
     *
     */
    public static void threadRunnable() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                getCurrentThreadPid();
                for(int i=0;i<Integer.MAX_VALUE;i++) {
//                    System.out.println(i);
                    int[] temp = new int[10];
                }
            }
        }, "Runnable - Thread");
        thread.start();
    }

    /**
     * Blocked
     "Blocked ThreadB" #10 prio=5 os_prio=0 tid=0x1764c000 nid=0x1af0 waiting for monitor entry [0x17ddf000]
     java.lang.Thread.State: BLOCKED (on object monitor)
     at com.qiuqiu.learn.multithreading.ThreadState$3.run(ThreadState.java:79)
     - waiting to lock <0x071c1d80> (a java.lang.Object)
     at java.lang.Thread.run(Thread.java:748)

     "Blocked ThreadA" #9 prio=5 os_prio=0 tid=0x1764b800 nid=0x23c8 waiting on condition [0x17d4f000]
     java.lang.Thread.State: TIMED_WAITING (sleeping)
     at java.lang.Thread.sleep(Native Method)
     at com.qiuqiu.learn.multithreading.ThreadState$2.run(ThreadState.java:68)
     - locked <0x071c1d80> (a java.lang.Object)
     at java.lang.Thread.run(Thread.java:748)
     */
    public static void threadBlocked() {
        Object lock = new Object();
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized(lock) {
                    System.out.println(Thread.currentThread().getName() + " invoke");
                    try {
                        Thread.sleep(200001);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "Blocked ThreadA");
        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    System.out.println(Thread.currentThread().getName() + " invoke");
                    try {
                        Thread.sleep(200001);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "Blocked ThreadB");
        getCurrentThreadPid();
        threadA.start();
        threadB.start();
    }

    /**
     * Waiting - Object.wait();
     *
     "Waiting ThreadB" #10 prio=5 os_prio=0 tid=0x179b4400 nid=0x1dec waiting on condition [0x1813f000]
     java.lang.Thread.State: TIMED_WAITING (sleeping)
     at java.lang.Thread.sleep(Native Method)
     at com.qiuqiu.learn.multithreading.ThreadState$5.run(ThreadState.java:127)
     - locked <0x075c20d8> (a java.lang.Object)
     at java.lang.Thread.run(Thread.java:748)

     "Waiting ThreadA" #9 prio=5 os_prio=0 tid=0x179b3800 nid=0x2694 in Object.wait() [0x180af000]
     java.lang.Thread.State: WAITING (on object monitor)
     at java.lang.Object.wait(Native Method)
     - waiting on <0x075c20d8> (a java.lang.Object)
     at java.lang.Object.wait(Object.java:502)
     at com.qiuqiu.learn.multithreading.ThreadState$4.run(ThreadState.java:113)
     - locked <0x075c20d8> (a java.lang.Object)
     at java.lang.Thread.run(Thread.java:748)

     */
    public static void threadWaitingObjectWait() {
        Object lock = new Object();
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    try {
                        lock.wait();
                        System.out.println("wait over");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, "Waiting ThreadA");
        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    try {
                        Thread.sleep(200001);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    lock.notifyAll();
                }
            }
        }, "Waiting ThreadB");
        getCurrentThreadPid();
        threadA.start();
        threadB.start();
    }

    /**
     * Waiting Thread.join()
     *
     "main" #1 prio=5 os_prio=0 tid=0x0549d800 nid=0x1c10 in Object.wait() [0x04eef000]
     java.lang.Thread.State: WAITING (on object monitor)
     at java.lang.Object.wait(Native Method)
     - waiting on <0x079c35d0> (a java.lang.Thread)
     at java.lang.Thread.join(Thread.java:1252)
     - locked <0x079c35d0> (a java.lang.Thread)
     at java.lang.Thread.join(Thread.java:1326)
     at com.qiuqiu.learn.multithreading.ThreadState.threadWaitingThreadJoin(ThreadState.java:181)
     at com.qiuqiu.learn.multithreading.ThreadState.main(ThreadState.java:12)

     "ThreadA" #9 prio=5 os_prio=0 tid=0x17dbe800 nid=0x3e80 waiting on condition [0x1833f000]
     java.lang.Thread.State: TIMED_WAITING (sleeping)
     at java.lang.Thread.sleep(Native Method)
     at com.qiuqiu.learn.multithreading.ThreadState$6.run(ThreadState.java:171)
     at java.lang.Thread.run(Thread.java:748)
     */
    public static void threadWaitingThreadJoin() {
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(200000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("ThreadA over");
            }
        }, "ThreadA");
        getCurrentThreadPid();
        threadA.start();
        try {
            threadA.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Waiting  Lock Condition await()
     *
     "ThreadB" #10 prio=5 os_prio=0 tid=0x179b5800 nid=0x20b8 waiting on condition [0x17f6f000]
     java.lang.Thread.State: TIMED_WAITING (sleeping)
     at java.lang.Thread.sleep(Native Method)
     at com.qiuqiu.learn.multithreading.ThreadState$8.run(ThreadState.java:229)
     at java.lang.Thread.run(Thread.java:748)

     "ThreadA" #9 prio=5 os_prio=0 tid=0x179b4c00 nid=0x2bb8 waiting on condition [0x17edf000]
     java.lang.Thread.State: WAITING (parking)
     at sun.misc.Unsafe.park(Native Method)
     - parking to wait for  <0x075e6908> (a java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject)
     at java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
     at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.await(AbstractQueuedSynchronizer.java:2039)
     at com.qiuqiu.learn.multithreading.ThreadState$7.run(ThreadState.java:218)
     at java.lang.Thread.run(Thread.java:748)
     *
     */
    public static void threadWaitingConditionWait() {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("ThreadA over");
                lock.unlock();
            }
        }, "ThreadA");
        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                condition.signalAll();
                System.out.println("ThreadB over");
                lock.unlock();
            }
        }, "ThreadB");
        getCurrentThreadPid();
        threadA.start();
        threadB.start();
    }


    /**
     * TIMED_WAITING
     */
    public static void threadTimeWaiting() {
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("ThreadA over");
            }
        }, "ThreadA");
        threadA.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ThreadA state: " + threadA.getState());
    }

    /**
     * TERMINATED
     */
    public static void threadTerminated() {
        Thread threadA = new Thread();
        threadA.start();
        try {
            Thread.sleep(5000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(threadA.getState()); // TERMINATED
    }

}
