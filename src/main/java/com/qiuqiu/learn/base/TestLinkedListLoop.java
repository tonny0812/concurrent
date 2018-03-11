package com.qiuqiu.learn.base;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * for循环适合访问顺序存储结构，可以根据下标快速获取指定元素（即支持随机访问）。
 * 而Iterator 适合访问链式存储结构，因为迭代器是通过next()和Pre()来定位的，但它也可以访问顺序存储结构的集合。
 */
public class TestLinkedListLoop {
    private static List<Integer> list;
    private static int runCounts, listSize, value;

    static {
        // 准备数据阶段
        list = new LinkedList<>();
        for (int i = 0; i < 100000; i++) {
            list.add(i);
        }
        // 测试阶段
        runCounts = 1; // 执行次s数
        listSize = list.size();
    }

    public static void main(String[] args) {
        TestLinkedListLoop.loopTest();
    }

    /**
     *  loopOfFor: 7810ms
     *  loopOfForeach: 1ms
     *  loopOfIterator: 2ms
     */
    public static void loopTest() {
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
