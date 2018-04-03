package com.qiuqiu.learn.multithreading;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ThreadNotifyTest {
    public static void main(String [] args) throws Exception {
//        WaiterMessageTest.start();

        WaitNotifyTest.start();
    }
}

class WaiterMessageTest {
    public static void start() {
        Message msg = new Message("process it");
        Waiter waiter1 = new Waiter(msg);
        new Thread(waiter1, "John").start();

        Waiter waiter = new Waiter(msg);
        new Thread(waiter,"Tom").start();

        Notifier notifier = new Notifier(msg);
        new Thread(notifier, "上帝").start();
        System.out.println("All the threads are started");
    }
}

class Message {
    private String msg;
    public Message(String str){
        this.msg=str;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String str) {
        this.msg=str;
    }
}
class Waiter implements Runnable{
    private Message msg;
    public Waiter(Message m){
        this.msg=m;
    }
    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        synchronized (msg) {
            try{
                System.out.println(name+" waiting to get notified at time:"+System.currentTimeMillis());
                msg.wait();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            System.out.println(name+" waiter thread got notified at time:"+System.currentTimeMillis());
            //process the message now
            System.out.println(name+" processed: "+msg.getMsg());
        }
    }
}

class Notifier implements Runnable {
    private Message msg;
    public Notifier(Message msg) {
        this.msg = msg;
    }
    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println(name+" started");
        try {
            Thread.sleep(1000);
            synchronized (msg) {
                msg.setMsg(name+" Notifier work done");
                msg.notify();
//                msg.notifyAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class WaitNotifyTest {
    /**
     * 只有一个生产者和消费者，
     * 等待池中始终只有一个线程，
     * 要么就是生产者唤醒消费者，
     * 要么消费者唤醒生产者，
     * 所以程序可以成功跑起来
     *
     *
     * 两个消费者C1，C2，一个生产者P，
     * 当生产者P生产出一个产品，之后notify，该生产者进程进入等待线程池，
     * 然后通知等待池中的线程，随机选出一个线程进入锁池，
     * 如果选中生产者P，由于cache有一个产品，所以P进入等待池中，
     *
     * 如果选中一个消费者C1，则消费，notify，之后进入等待池，
     * 这时如果消费者C1或者C2进入锁池，那么由于cache为空，所以该消费者会进入等待池中，
     * 造成三个线程都在等待池中，发生死锁。
     * 使用notifyAll后，所有在等待池中的线程进入锁池，不可能出现全在等待池中的现象。
     * @throws Exception
     */
    public static void start() throws Exception {
        List<Integer> cache = new ArrayList<>();
        new Thread(new Consumer(cache), "consumer-1").start();
        new Thread(new Consumer(cache), "consumer-2").start();

        new Thread(new Producer(cache), "producer-1").start();
//        new Thread(new Producer(cache), "producer-2").start();
//        new Thread(new Producer(cache), "producer-3").start();

        SystemUtils.getCurrentThreadPid();
    }
}

class Producer implements Runnable {
    List<Integer> cache;

    public Producer(List<Integer> cache) {
        this.cache = cache;
    }

    @Override
    public void run() {
        while (true) {
            produce();
        }
    }
    private void produce() {
        synchronized (cache) {
            try {
                while (cache.size() == 1) {
                    System.out.println(Thread.currentThread().getName() + " will go into wait!");
                    cache.wait();
                    System.out.println(Thread.currentThread().getName() + " has actived!");
                }
                // 模拟一秒生产一条消息
                Thread.sleep(1000);
                Integer p = new Random().nextInt();
                cache.add(p);
                System.out.println(Thread.currentThread().getName() + " produced " + "[" + p + "]");
                cache.notify();
//                cache.notifyAll();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer implements Runnable {
    List<Integer> cache;

    public Consumer(List<Integer> cache) {
        this.cache = cache;
    }

    @Override
    public void run() {
        while (true) {
            consume();
        }
    }
    private void consume() {
        synchronized (cache) {
            try {
                while (cache.isEmpty()) {
                    System.out.println(Thread.currentThread().getName() + " will go into wait!");
                    cache.wait();
                    System.out.println(Thread.currentThread().getName() + " has actived!");
                }
                System.out.println(Thread.currentThread().getName() + " consumed [" + cache.remove(0) + "]");
                cache.notify();
//                cache.notifyAll();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}