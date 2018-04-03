package com.qiuqiu.learn.base;

public class FinallyTest {

    public static String test() {
        try {
            return "try";
        } catch (Exception e) {

        } finally {
            System.out.println("finally");
        }
        return "test";
    }

    public static void main(String[] args) {
        System.out.println(FinallyTest.test());
    }
}
