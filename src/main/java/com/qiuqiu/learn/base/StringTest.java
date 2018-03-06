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
    private String str = "str";

}
