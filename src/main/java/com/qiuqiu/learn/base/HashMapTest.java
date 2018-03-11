package com.qiuqiu.learn.base;

import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class HashMapTest {
    private static Map<Integer, String> hashMap = null;
    private static int runCounts, mapSize;

    static {
        hashMap = new HashMap<Integer, String>();
        mapSize = 10000;
        for(int index=0;index<mapSize;index++) {
            hashMap.put(Integer.valueOf(index), "v_"+index);
        }
        // 测试阶段
        runCounts = 100; // 执行次s数
    }

    /**
     * 遍历Map的方式有很多，通常场景下我们需要的是遍历Map中的Key和Value，那么推荐使用的、效率最高的方式是
     */

    public static void bestLoop() {
        Set<Map.Entry<Integer, String>> entrySet = hashMap.entrySet();
        Iterator<Map.Entry<Integer, String>> iter = entrySet.iterator();
        while (iter.hasNext()) {
            Map.Entry<Integer, String> entry = iter.next();
            System.out.println(entry.getKey() + "\t" + entry.getValue());
        }
    }


    /**
     * Map的四种遍历方式:
     *
     * (1) for each map.entrySet()
     * (2) 显示调用map.entrySet()的集合迭代器
     * (3) for each map.keySet()，再调用get获取
     * (4) for each map.entrySet()，用临时变量保存map.entrySet()
     */
    public static void loopTest() {
        // (1) for each map.entrySet()
        long startTime1 = System.currentTimeMillis();
        for (int i = 0; i < runCounts; i++) {
            HashMapTest.testForEachMap(hashMap);
        }
        long endTime1 = System.currentTimeMillis();

        // (2) 显示调用map.entrySet()的集合迭代器
        long startTime2 = System.currentTimeMillis();
        for (int i = 0; i < runCounts; i++) {
            HashMapTest.testIterator(hashMap);
        }
        long endTime2 = System.currentTimeMillis();

        // (3) for each map.keySet()，再调用get获取
        long startTime3 = System.currentTimeMillis();
        for (int i = 0; i < runCounts; i++) {
            HashMapTest.testKeyGet(hashMap);
        }
        long endTime3 = System.currentTimeMillis();

        // (3) for each map.keySet()，再调用get获取
        long startTime4 = System.currentTimeMillis();
        for (int i = 0; i < runCounts; i++) {
            HashMapTest.testEntrySet(hashMap);
        }
        long endTime4 = System.currentTimeMillis();

        System.out.println("testForEachMap: " + (endTime1-startTime1)+ "ms");
        System.out.println("testIterator: "+ (endTime2-startTime2)+ "ms");
        System.out.println("testKeyGet: "+ (endTime3-startTime3)+ "ms");
        System.out.println("testEntrySet: "+ (endTime4-startTime4)+ "ms");
    }

    /**
     * 1) for each map.entrySet()
     */
    public static void testForEachMap(Map<Integer, String> hashMap) {
        for (Map.Entry<Integer, String> entry : hashMap.entrySet()) {
            entry.getKey();
            entry.getValue();
        }
    }

    /**
     * 2) 显示调用map.entrySet()的集合迭代器
     */
    public static void testIterator(Map<Integer, String> hashMap) {
        Iterator<Map.Entry<Integer, String>> iterator = hashMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, String> entry = iterator.next();
            entry.getKey();
            entry.getValue();
        }
    }

    /**
     * 3) for each map.keySet()，再调用get获取
     */
    public static void testKeyGet(Map<Integer, String> hashMap) {
        for (Integer key : hashMap.keySet()) {
            hashMap.get(key);
        }
    }

    /**
     * 4) for each map.entrySet()，用临时变量保存map.entrySet()
     */
    public static void testEntrySet(Map<Integer, String> hashMap) {
        Set<Map.Entry<Integer, String>> entrySet = hashMap.entrySet();
        for (Map.Entry<Integer, String> entry : entrySet) {
            entry.getKey();
            entry.getValue();
        }
    }

    public static void testHashMap() {

        Set<Integer> keyset = hashMap.keySet();

        /**
         * Iterator迭代器方式
         * 迭代器是一种模式，它可以使得对于序列类型的数据结构的遍历行为与被遍历的对象分离，
         * 即我们无需关心该序列的底层结构是什么样子的。只要拿到这个对象,使用迭代器就可以遍历这个对象的内部。
         */

        Iterator<Integer> iterator = keyset.iterator();
        while(iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        /**
         *  foreach语句
         *  foreach语句是java5的新特征之一，在遍历数组、集合方面，foreach为开发人员提供了极大的方便。
         *
         *
          */

        for(Integer key : hashMap.keySet()) {
            System.out.println(key);
        }

        /**
         *  ArrayList
         * 随机访问，它是通过索引值去遍历
         *
         * 由于ArrayList实现了RandomAccess接口，它支持通过索引值去随机访问元素
         */
        List<Integer> list = new ArrayList<>();
        for(int index=0; index<list.size(); index++) {
            System.out.println(list.get(index));
        }

        Collection<String> values = hashMap.values();
        Iterator<String> iterator2 = values.iterator();
        while(iterator2.hasNext()) {
            System.out.println(iterator.next());
        }

        for(String value : hashMap.values()) {
            System.out.println(value);
        }

        for(Map.Entry<Integer, String> entry : hashMap.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

        hashMap = new ConcurrentHashMap<Integer, String>();

    }

    public static void testHashTable() {
        Hashtable table = new Hashtable();
        table.put("1", "value1");
        table.put("2", "value2");
        table.put("3", "value3");
        table.put("4", "value4");
        table.put("5", "value5");

        Enumeration em = table.elements();
        while (em.hasMoreElements()) {
            String obj = (String) em.nextElement();
            System.out.println(obj);

            int hash = hash(obj);
            int i = indexFor(hash, table.size());
            System.out.println("hash:"+hash + "----index:"+i);
        }


    }

    private static int hash(Object x) {
        int h = x.hashCode();
        h += ~(h << 9);
        h ^= (h >>> 14);
        h += (h << 4);
        h ^= (h >>> 10);
        return h;
    }

    static int indexFor(int h, int length) {
        return h & (length - 1);
    }

    public static void main(String[] args) {
//        HashMapTest.testHashTable();
//        HashMapTest.testHashMap();
//        for(int index=0;index<5;index++) {
//            System.out.println("-------------*****------------------");
//            HashMapTest.loopTest();
//        }
        System.out.print("compare loop performance of HashMap");
        loopMapCompare(getHashMaps(10000, 100000, 1000000, 2000000));
    }


    public static Map<String, String>[] getHashMaps(int... sizeArray) {
        Map<String, String>[] mapArray = new HashMap[sizeArray.length];
        for (int i = 0; i < sizeArray.length; i++) {
            int size = sizeArray[i];
            Map<String, String> map = new HashMap<String, String>();
            for (int j = 0; j < size; j++) {
                String s = Integer.toString(j);
                map.put(s, s);
            }
            mapArray[i] = map;
        }
        return mapArray;
    }

    /**
     *
     compare loop performance of HashMap
     -----------------------------------------------------------------------
     map size               | 10,000    | 100,000   | 1,000,000 | 2,000,000
     -----------------------------------------------------------------------
     for each entrySet      | 2 ms      | 3 ms      | 19 ms     | 41 ms
     -----------------------------------------------------------------------
     for iterator entrySet  | 0 ms      | 2 ms      | 20 ms     | 40 ms
     -----------------------------------------------------------------------
     for each keySet        | 1 ms      | 3 ms      | 25 ms     | 54 ms
     -----------------------------------------------------------------------
     for entry : entrySet() | 0 ms      | 3 ms      | 19 ms     | 42 ms
     -----------------------------------------------------------------------

     * @param mapArray
     */
    public static void loopMapCompare(Map<String, String>[] mapArray) {
        printHeader(mapArray);
        long startTime, endTime;

        // Type 1
        for (int i = 0; i < mapArray.length; i++) {
            Map<String, String> map = mapArray[i];
            startTime = Calendar.getInstance().getTimeInMillis();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                entry.getKey();
                entry.getValue();
            }
            endTime = Calendar.getInstance().getTimeInMillis();
            printCostTime(i, mapArray.length, "for each entrySet", endTime - startTime);
        }

        // Type 2
        for (int i = 0; i < mapArray.length; i++) {
            Map<String, String> map = mapArray[i];
            startTime = Calendar.getInstance().getTimeInMillis();
            Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                entry.getKey();
                entry.getValue();
            }
            endTime = Calendar.getInstance().getTimeInMillis();
            printCostTime(i, mapArray.length, "for iterator entrySet", endTime - startTime);
        }

        // Type 3
        for (int i = 0; i < mapArray.length; i++) {
            Map<String, String> map = mapArray[i];
            startTime = Calendar.getInstance().getTimeInMillis();
            for (String key : map.keySet()) {
                map.get(key);
            }
            endTime = Calendar.getInstance().getTimeInMillis();
            printCostTime(i, mapArray.length, "for each keySet", endTime - startTime);
        }

        // Type 4
        for (int i = 0; i < mapArray.length; i++) {
            Map<String, String> map = mapArray[i];
            startTime = Calendar.getInstance().getTimeInMillis();
            Set<Map.Entry<String, String>> entrySet = map.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                entry.getKey();
                entry.getValue();
            }
            endTime = Calendar.getInstance().getTimeInMillis();
            printCostTime(i, mapArray.length, "for entry : entrySet()", endTime - startTime);
        }
    }

    static int FIRST_COLUMN_LENGTH = 23, OTHER_COLUMN_LENGTH = 12, TOTAL_COLUMN_LENGTH = 71;
    static final DecimalFormat COMMA_FORMAT = new DecimalFormat("#,###");

    public static void printHeader(Map... mapArray) {
        printRowDivider();
        for (int i = 0; i < mapArray.length; i++) {
            if (i == 0) {
                StringBuilder sb = new StringBuilder().append("map size");
                while (sb.length() < FIRST_COLUMN_LENGTH) {
                    sb.append(" ");
                }
                System.out.print(sb);
            }

            StringBuilder sb = new StringBuilder().append("| ").append(COMMA_FORMAT.format(mapArray[i].size()));
            while (sb.length() < OTHER_COLUMN_LENGTH) {
                sb.append(" ");
            }
            System.out.print(sb);
        }
        TOTAL_COLUMN_LENGTH = FIRST_COLUMN_LENGTH + OTHER_COLUMN_LENGTH * mapArray.length;
        printRowDivider();
    }

    public static void printRowDivider() {
        System.out.println();
        StringBuilder sb = new StringBuilder();
        while (sb.length() < TOTAL_COLUMN_LENGTH) {
            sb.append("-");
        }
        System.out.println(sb);
    }

    public static void printCostTime(int i, int size, String caseName, long costTime) {
        if (i == 0) {
            StringBuilder sb = new StringBuilder().append(caseName);
            while (sb.length() < FIRST_COLUMN_LENGTH) {
                sb.append(" ");
            }
            System.out.print(sb);
        }

        StringBuilder sb = new StringBuilder().append("| ").append(costTime).append(" ms");
        while (sb.length() < OTHER_COLUMN_LENGTH) {
            sb.append(" ");
        }
        System.out.print(sb);

        if (i == size - 1) {
            printRowDivider();
        }
    }
}
