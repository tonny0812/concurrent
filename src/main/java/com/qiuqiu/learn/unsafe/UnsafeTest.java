package com.qiuqiu.learn.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeTest {

    private int i = 0;

    /**
     * 通过反射获取Unsafe实例，
     * Unsafe做操作的是直接内存区，所以该类没有办法通过HotSpot的GC进行回收，需要进行手动回收，
     * 因此在使用此类时需要注意内存泄漏（Memory Leak）和内存溢出（Out Of Memory）
     */
    private static Unsafe getInstance() {
        Field f = null;
        try {
            f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            Unsafe unsafe = (Unsafe) f.get(null);
            return unsafe;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Unsafe unsafe = Unsafe.getUnsafe();
        //获取字段i在内存中偏移量
        long offset = 0;
        try {
            offset = unsafe.objectFieldOffset(UnsafeTest.class.getDeclaredField("i"));
            //创建对象实例，设置字段的值
            UnsafeTest unsafeDemo = new UnsafeTest();
            unsafe.putInt(unsafeDemo, offset, 100);
            //打印结果
            System.out.println(unsafeDemo.i);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }


    }
}
