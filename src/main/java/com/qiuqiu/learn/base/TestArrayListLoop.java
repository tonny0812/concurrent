package com.qiuqiu.learn.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * ArrayList实现了RandomAccess随机访问接口，因此它对随机访问的速度快，
 * 而基本的for循环中的get()方法，采用的即是随机访问的方法，因而在ArrayList中，for循环速度快。
 * LinkedList采取的是顺序访问方式，iterator中的next()方法，采用的即是顺序访问方法，因此在LinkedList中，使用iterator的速度较快。
 */
public class TestArrayListLoop {

    private static List<Integer> list;
    private static int runCounts, listSize, value;

    static {
        // 准备数据阶段
        list = new ArrayList<Integer>();
        for (int i = 0; i < 100000; i++)
        {
            list.add(i);
        }
        // 测试阶段
        runCounts = 1000; // 执行次s数
        listSize = list.size();
    }

    public static void main(String[] args) {
        TestArrayListLoop.loopTest();
    }

    /**
     * loopOfFor: 310ms
     * loopOfForeach: 590ms
     * loopOfIterator: 577ms
     *
     * 从实验结果来看，在遍历ArrayList中，
     * 效率最高的是loopOfFor，loopOfForeach和loopOfIterator之间关系不明确，
     * 但在增大运行次数时，loopOfIterator效率高于loopOfForeach。
     *
     *
     */
    private static void loopTest() {
        // For循环的测试
        long startTime1 = System.currentTimeMillis();
        for (int i = 0; i < runCounts; i++) {
            loopOfFor(list);
        }
        long endTime1 = System.currentTimeMillis();

        // Foreach循环的测试
        long startTime2 = System.currentTimeMillis();
        for (int i = 0; i < runCounts; i++) {
            loopOfForeach(list);
        }
        long endTime2 = System.currentTimeMillis();

        // Iterator迭代器的测试
        long startTime3 = System.currentTimeMillis();
        for (int i = 0; i < runCounts; i++) {
            loopOfIterator(list);
        }
        long endTime3 = System.currentTimeMillis();

        System.out.println("loopOfFor: " + (endTime1-startTime1)+ "ms");
        System.out.println("loopOfForeach: "+ (endTime2-startTime2)+ "ms");
        System.out.println("loopOfIterator: "+ (endTime3-startTime3)+ "ms");
    }

    /**
     * 由于ArrayList实现了RandomAccess接口，它支持通过索引值去随机访问元素。
     * @param list
     */
    public static void loopOfFor(List<Integer> list) {
        int value;
        int size = list.size();
        // 基本的for
        for (int i = 0; i < size; i++)
        {
            value = list.get(i);
        }
    }

    /**
     * 使用forecah方法遍历数组
     * @param list
     */
    public static void loopOfForeach(List<Integer> list) {
        int value;
        // foreach
        for (Integer integer : list)
        {
            value = integer;
        }
    }

    /**
     * 通过迭代器方式遍历数组
     * @param list
     */
    public static void loopOfIterator(List<Integer> list) {
        int value;
        // iterator
        for (Iterator<Integer> iterator = list.iterator(); iterator.hasNext();)
        {
            value = iterator.next();
        }
    }
}
