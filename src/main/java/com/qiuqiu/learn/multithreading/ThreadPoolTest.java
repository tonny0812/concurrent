package com.qiuqiu.learn.multithreading;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

/**
 *
 */
public class ThreadPoolTest {
    static class MyThread implements Runnable {
        private String name;

        public MyThread(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            // 做点事情
            try {
                Thread.sleep(1000);

                System.out.println(name + " finished job!") ;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class MyThreadCallable implements Callable {
        private String name;

        public MyThreadCallable(String name) {
            this.name = name;
        }

        @Override
        public Object call() throws Exception {
            // 做点事情
            try {
                Thread.sleep(1000);

                System.out.println(name + " finished job!") ;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return name;
        }
    }

    public static void main(String[] args) {
        // 创建线程池，为了更好的明白运行流程，增加了一些额外的代码
//        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(2);
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
//        BlockingQueue<Runnable> queue = new PriorityBlockingQueue<Runnable>();
//        BlockingQueue<Runnable> queue = new SynchronousQueue<Runnable>();


        // AbortPolicy/CallerRunsPolicy/DiscardOldestPolicy/DiscardPolicy
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 4, 5, TimeUnit.SECONDS,
                queue, new ThreadPoolExecutor.AbortPolicy());
        List<Future> futureList = new ArrayList<>();
        // 向线程池里面扔任务
        for (int i = 0; i < 10; i++) {
            System.out.println("当前线程池大小[" + threadPool.getPoolSize() + "],当前队列大小[" + queue.size() + "]");

            threadPool.execute(new MyThread("Thread" + i));
            Future future = threadPool.submit(new MyThreadCallable("ThreadCallable----" + i));
            futureList.add(future);
        }
        // 关闭线程池
        threadPool.shutdown();

        while(futureList.size()>0) {
            for(Iterator iterator = futureList.iterator();iterator.hasNext();) {
                Future f = (Future) iterator.next();
                if(f.isDone()) {
                    try {
                        System.out.println(">>>>>>>" + f.get().toString());
                        iterator.remove();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
