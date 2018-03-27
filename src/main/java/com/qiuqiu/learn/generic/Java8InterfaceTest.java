package com.qiuqiu.learn.generic;

public interface Java8InterfaceTest {
    default void default_speak() {
        System.out.println("default speak");
    }

    public static void static_speak() {
        System.out.println("static speak");
    }

    public void speak();

}
