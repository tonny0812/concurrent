package com.qiuqiu.learn.base;

/**
 * 是否可变
 * String 不可变，StringBuffer 和 StringBuilder 可变。
 * 是否线程安全
 * String 不可变，因此是线程安全的。
 * StringBuilder 不是线程安全的；StringBuffer 是线程安全的，使用 synchronized 来同步。
 *
 */
public class StringTest {
    /**
     * String 不可变的原因
     */
    private String strA = "Hello";
    private String strB = new String("Hello");
    private String strC = "He" + "llo";
    private String strD = new String("He") + new String("llo");

    public static int num = 1;

    public void test() {
        System.out.println(strA.equals(strB));
        System.out.println(strA == strC);
        System.out.println(strA == strD);

        System.out.println("strA's hashcode: " + strA.hashCode());
        System.out.println("strD's hashcode: " + strD.hashCode());
    }

    public static void main(String[] args) {
        StringTest t = new StringTest();
        t.test();
    }




}
