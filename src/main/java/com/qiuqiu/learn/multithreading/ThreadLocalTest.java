package com.qiuqiu.learn.multithreading;

import java.util.Random;

/**
 * ThreadLocal并不是一个Thread，而是Thread的局部变量
 *
 * ThreadLocal使用步骤
 * 1.建立ThreadLocal容器对象A，其中对需要保存的属性进行封装。并提供相应的get/set方法（全部为static）
 * 2.在客户端程序中，用A.setxxx, A.getXXX访问相应数据，即可保证每个线程访问的是自己独立的变量
 *
 * ThreadLocal是隔离多个线程的数据共享，从根本上就不在多个线程之间共享资源（变量），
 * 这样当然不需要对多个线程进行同步了。所以，如果你需要进行多个线程之间进行通信，则使用同步机制；
 * 如果需要隔离多个线程之间的共享冲突，可以使用ThreadLocal，这将极大地简化你的程序，使程序更加易读、简洁
 *
 * 每个使用ThreadLocal的Thread类中都保存有一个ThreadLocalMap，用于保存Thread实例存入的相关信息。
 * ThreadLocal整体上给我的感觉就是，一个包装类
 */
public class ThreadLocalTest {
    public static void main(String[] args) {
        Thread threadOne = new ThreadLocalDemo();
        threadOne.start();
        Thread threadTwo = new ThreadLocalDemo();
        threadTwo.start();
    }
}

/**
 * 创建一个Context类，其中含有transactionId属性
 */
class Context {
    private String transactionId = null;
    public String getTransactionId() {
        return transactionId;
    }
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}

/**
 * 创建MyThreadLocal做为容器，将一个Context对象保存于ThreadLocal中
 */
class MyThreadLocal {
    public static final ThreadLocal<Context> userThreadLocal = new ThreadLocal<>();
    public static void set(Context user) {
        userThreadLocal.set(user);
    }
    public static void unset() {
        userThreadLocal.remove();
    }
    public static Context get() {
        return userThreadLocal.get();
    }
}

/**
 * 多线程客户端程序
 */
class ThreadLocalDemo extends Thread {

    @Override
    public void run() {
        // 线程
        Context context = new Context();
        Random random = new Random();
        int age = random.nextInt(100);
        context.setTransactionId(String.valueOf(age));

        System.out.println("set thread ["+getName()+"] contextid to " + String.valueOf(age));
        // 在ThreadLocal中设置context
        MyThreadLocal.set(context);
        /* note that we are not explicitly passing the transaction id */
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        new BusinessService().businessMethod();
        MyThreadLocal.unset();
    }
}

/**
 * 模拟业务层，在某处读取context对象
 */
class BusinessService {
    public void businessMethod() {
        Context context = MyThreadLocal.get();
        System.out.println(context.getTransactionId());
    }
}
