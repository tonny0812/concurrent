package com.qiuqiu.learn.generic;

public class Java8InterfaceTestImpl implements Java8InterfaceTest {
    public static void main(String[] args) {
        Java8InterfaceTestImpl impl = new Java8InterfaceTestImpl();
        impl.default_speak();
        Java8InterfaceTest.static_speak();
        impl.speak();
        Java8InterfaceTestImpl.static_speak();
    }

    @Override
    public void speak() {
        System.out.println("impl speak");
    }

    static void static_speak() {
        System.out.println("impl static speak");
    }
}
