package com.qiuqiu.learn.hashmap;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HashTableTest {
    public static Map<String, String> hashTable;
    public static Map<String, String> hashMap;
    public static Map<String, String> concurrentHashMap;
    public static Map<String, String> synchronizedHashMap;
    public static void main(String[] args) {
        hashTable = new Hashtable<>();
        Thread thread1 = new Thread("thread1") {
            @Override
            public void run() {
                for(int index=0;index<5000;index++)
                    hashTable.put(String.valueOf(index), String.valueOf(index));
                System.out.println(Thread.currentThread().getName() + "——>" + hashTable.size());

            }
        };
        Thread thread2 = new Thread("thread2") {
            @Override
            public void run() {
                for(int index=0;index<5000;index++) {
                    hashTable.put(String.valueOf(index), String.valueOf(index));
                }
                System.out.println(Thread.currentThread().getName() + "——>" + hashTable.size());
            }
        };
        thread1.start();
        thread2.start();

        hashMap = new HashMap<>();
        Thread thread4 = new Thread("thread4") {
            @Override
            public void run() {
                for(int index=0;index<5000;index++)
                    hashMap.put(String.valueOf(index), String.valueOf(index));
                System.out.println(Thread.currentThread().getName() + "——>" + hashMap.size());

            }
        };
        Thread thread3 = new Thread("thread3") {
            @Override
            public void run() {
                for(int index=0;index<5000;index++) {
                    hashMap.put(String.valueOf(index), String.valueOf(index));
                }
                System.out.println(Thread.currentThread().getName() + "——>" + hashMap.size());
                System.out.println(Thread.currentThread().getName() + "——>" + hashMap.keySet().size());
            }
        };
        thread3.start();
        thread4.start();

        concurrentHashMap = new ConcurrentHashMap<>();
        Thread thread5 = new Thread("thread5") {
            @Override
            public void run() {
                for(int index=0;index<5000;index++)
                    concurrentHashMap.put(String.valueOf(index), String.valueOf(index));
                System.out.println(Thread.currentThread().getName() + "——>" + concurrentHashMap.size());

            }
        };
        Thread thread6 = new Thread("thread6") {
            @Override
            public void run() {
                for(int index=0;index<5000;index++) {
                    concurrentHashMap.put(String.valueOf(index), String.valueOf(index));
                }
                System.out.println(Thread.currentThread().getName() + "——>" + concurrentHashMap.size());
                System.out.println(Thread.currentThread().getName() + "——>" + concurrentHashMap.keySet().size());
            }
        };
        thread5.start();
        thread6.start();

        synchronizedHashMap = Collections.synchronizedMap(new HashMap<>());
        Thread thread7 = new Thread("thread7") {
            @Override
            public void run() {
                for(int index=0;index<5000;index++) {
                    synchronizedHashMap.put(String.valueOf(index), String.valueOf(index));
                }
                System.out.println(Thread.currentThread().getName() + "——》" + synchronizedHashMap.size());
            }
        };
        Thread thread8 = new Thread("thread8") {
            @Override
            public void run() {
                for(int index=0;index<5000;index++) {
                    synchronizedHashMap.put(String.valueOf(index), String.valueOf(index));
                }
                System.out.println(Thread.currentThread().getName() + "——》" + synchronizedHashMap.size());
            }
        };
        thread7.start();
        thread8.start();
    }
}
