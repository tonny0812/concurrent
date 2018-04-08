package com.qiuqiu.learn.multithreading;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

public class ThreadExcutorsTest {
    public static void main(String[] args) {
        try {
            ExecutorsTest.test();
            System.out.println("-----------------");
            ExecutorsTest.test2();
            System.out.println("-----------------");
            ExecutorsTest.test3();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
class ExecutorsTest {
    private static Task A = new Task("A", 1000);
    private static Task B = new Task("B", 1500);
    private static Task C = new Task("C", 1000);
    private static Task D = new Task("D", 900);
    public static void test() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        List<Future<Result>> futures = new ArrayList<>();
        futures.add(executorService.submit(A));
        futures.add(executorService.submit(B));
        futures.add(executorService.submit(C));
        futures.add(executorService.submit(D));
        for (Future future:futures) {
            Result result = (Result) future.get();
            // rest of the code here.
            System.out.println(result);
        }
        executorService.shutdown();
    }

    public static void test2() {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        CompletionService executorCompletionService= new ExecutorCompletionService<>(executorService);
        List<Future<Integer>> futures = new ArrayList<>();
        futures.add(executorCompletionService.submit(A));
        futures.add(executorCompletionService.submit(B));
        futures.add(executorCompletionService.submit(C));
        futures.add(executorCompletionService.submit(D));

        for (int i=0; i<futures.size(); i++) {
            Result result = null;
            try {
                result = (Result) executorCompletionService.take().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            // Some processing here
            System.out.println(result);
        }
    }

    public static void test3() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        List<Callable<Result>> solvers = new ArrayList<>();
        solvers.add(A);
        solvers.add(B);
        solvers.add(C);
        solvers.add(D);
        try {
            solve(executorService, solvers);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }

    public static void solve(Executor e, Collection<Callable<Result>> solvers)
            throws InterruptedException {
        CompletionService<Result> ecs = new ExecutorCompletionService<Result>(e);
        int n = solvers.size();
        List<Future<Result>> futures = new ArrayList<Future<Result>>(n);
        Result result = null;
        try {
            for (Callable<Result> s : solvers)
                futures.add(ecs.submit(s));
            for (int i = 0; i < n; ++i) {
                try {
                    Result r = ecs.take().get();
                    if (r != null) {
                        result = r;
                        break;
                    }
                } catch(ExecutionException ignore) {}
            }
        }
        finally {
            for (Future<Result> f : futures)
                f.cancel(true);
        }

        if (result != null)
            System.out.println(result);

    }

}
class Task implements Callable<Result> {

    private String name;
    private int sleeptime = 1000;

    public Task(String name, int sleeptime) {
        this.name = name;
        this.sleeptime = sleeptime;
    }
    @Override
    public Result call() throws Exception {
        Thread.sleep(sleeptime);
        Result r = new Result();
        r.setValue(this.name);
        return r;
    }
}

class Result {
    private String value;
    public void setValue(String v) {
        this.value = v;
    }

    public String getValue() {
         return value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }
}
