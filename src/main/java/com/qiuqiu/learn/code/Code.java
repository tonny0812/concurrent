package com.qiuqiu.learn.code;

import java.util.*;
import java.util.concurrent.*;

public class Code {

    public static void main(String[] args) throws Exception {
        Code code = new Code();
        Map<String, String> map = new HashMap<String, String>();
        for(int i=0;i<20;i++) {
            map.put(String.valueOf(i), String.valueOf(i));
        }
        code.mapIterator(map);

        int nums[] = new int[] {1,2,5,7,7,8,9,11,11};
        System.out.println(code.binarySearch(nums, 7));

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Callable<Integer>> services = new ArrayList<Callable<Integer>>();
        for(int i=0;i<5;i++) {
            int num = i;
            services.add(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    Random rand = new Random();
                    int sleepTime = rand.nextInt(2000);
                    Thread.sleep(sleepTime);
                    return Integer.valueOf(num);
                }
            });
        }
        System.out.println("-----"+code.getFirstServiceResult(executorService, services)+"-----");
        executorService.shutdown();
    }

    public void mapIterator(Map<String, String> map) {
        if(null != map) {
            Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
            while(iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                System.out.println(entry.getKey() + ":" + entry.getValue());
            }
        }
    }

    public int binarySearch(int arr[], int key) {
        if(null != arr && arr.length > 0) {
            int start = 0, end = arr.length - 1;
            while(start < end) {
                int mid = (end - start) / 2 + start;
                if(arr[mid] > key) {
                    end = mid - 1;
                } else if(arr[mid] < key) {
                    start = mid + 1;
                } else {
                    return mid;
                }
            }
        }
        return -1;
    }

    public Integer getFirstServiceResult(Executor e, List<Callable<Integer>> services) throws Exception {
        CompletionService<Integer> ces = new ExecutorCompletionService<Integer>(e);
        int n = services.size();
        List<Future<Integer>> futures = new ArrayList<Future<Integer>>(n);
        Integer result = null;
        for(Callable<Integer> service : services) {
            futures.add(ces.submit(service));
        }
        for(int i=0;i<n;i++) {
            Integer r = ces.take().get();
            if(null != r) {
                result = r;
                break;
            }
        }
        for(Future<Integer> future : futures) {
            future.cancel(true);
        }
        return result;
    }
}
