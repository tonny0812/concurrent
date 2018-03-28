package com.qiuqiu.learn.multithreading;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class ThreadProduce {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadExtends threadExtends1  = new ThreadExtends("ThreadExtends1");
        ThreadExtends threadExtends2  = new ThreadExtends("ThreadExtends2");
        threadExtends1.start();
        threadExtends2.start();

        Thread threadRannalbleNew = new Thread(new ThreadRunnable("something", 123), "threadRannalble");
        threadRannalbleNew.start();

        //由Callable<Integer>创建一个FutureTask<Integer>对象：
        Callable<Object> oneCallable = new ThreadCallable("something");
        //注释：FutureTask<Integer>是一个包装器，它通过接受Callable<Integer>来创建，它同时实现了Future和Runnable接口。
        FutureTask<Object> oneTask = new FutureTask<Object>(oneCallable);
        oneTask.run();
        //由FutureTask<Integer>创建一个Thread对象：
        //至此，一个线程就创建完成了。
        Thread threadCallable = new Thread(oneTask, "threadCallable");
        System.out.println("Callable 返回结果："+oneTask.get().toString());
        threadCallable.start();

       /* try {
            ExecutorThread.test();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }
}

/**
 * 继承Thread类创建线程
 */
class ThreadExtends extends Thread {
    public ThreadExtends(String name) {
        super(name);
    }
    @Override
    public void run() {
        System.out.println("继承Thread："+ Thread.currentThread().getName());
    }
}

class SomeClass {
    private String name;
    private int number;
    public SomeClass(String name, int number) {
        this.name = name;
        this.number = number;
    }

    @Override
    public String toString() {
        return this.name + "--" + this.number;
    }
}

/**
 * 实现Runnable接口创建线程
 *
 * 如果自己的类已经extends另一个类，就无法直接extends Thread，此时，可以实现一个Runnable接口
 */
class ThreadRunnable extends SomeClass implements Runnable {
    public ThreadRunnable(String name, int number) {
        super(name, number);
    }

    @Override
    public void run() {
        System.out.println("实现Runnable接口："+ Thread.currentThread().getName());
    }
}

/**
 * 实现Callable接口通过FutureTask包装器来创建Thread线程
 */

class ThreadCallable implements Callable<Object> {

    private String taskName;

    ThreadCallable(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public Object call() throws Exception {
        System.out.println("实现Callable接口" + Thread.currentThread().getName());
        System.out.println(">>>" + taskName + "任务启动");
        Date dateTmp1 = new Date();
        Thread.sleep(1000);
        Date dateTmp2 = new Date();
        long time = dateTmp2.getTime() - dateTmp1.getTime();
        System.out.println(">>>" + taskName + "任务终止");
        return taskName + "任务返回运行结果,当前任务时间【" + time + "毫秒】";
    }
}
/**
 * 使用ExecutorService、Callable、Future实现有返回结果的线程
 *
 * ExecutorService、Callable、Future三个接口实际上都是属于Executor框架。
 * 返回结果的线程是在JDK1.5中引入的新特征，有了这种特征就不需要再为了得到返回值而大费周折了。而且自己实现了也可能漏洞百出。
 * 可返回值的任务必须实现Callable接口。类似的，无返回值的任务必须实现Runnable接口。
 * 执行Callable任务后，可以获取一个Future的对象，在该对象上调用get就可以获取到Callable任务返回的Object了。
 * 注意：get方法是阻塞的，即：线程无返回结果，get方法会一直等待。
 * 再结合线程池接口ExecutorService就可以实现传说中有返回结果的多线程了。
 * 下面提供了一个完整的有返回结果的多线程测试例子
 */
class ExecutorThread {
    public static void test() throws ExecutionException, InterruptedException {
        System.out.println("----程序开始运行----");
        Date date1 = new Date();

        int taskSize = 5;
        // 创建一个线程池
        ExecutorService pool = Executors.newFixedThreadPool(taskSize);
        // 创建多个有返回值的任务
        List<Future> list = new ArrayList<Future>();
        for (int i = 0; i < taskSize; i++) {
            Callable c = new ThreadCallable(i + " ");
            // 执行任务并获取Future对象
            Future f = pool.submit(c);
            // System.out.println(">>>" + f.get().toString());
            list.add(f);
        }
        // 关闭线程池
        pool.shutdown();

        // 获取所有并发任务的运行结果
        for (Future f : list) {
            // 从Future对象上获取任务的返回值，并输出到控制台
            System.out.println(">>>" + f.get().toString());
        }

        Date date2 = new Date();
        System.out.println("----程序结束运行----，程序运行时间【"
                + (date2.getTime() - date1.getTime()) + "毫秒】");
    }
}
