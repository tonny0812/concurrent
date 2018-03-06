package com.qiuqiu.learn.base;

public class BaseTest {
    private static String staticField = "静态变量";
    static {
        System.out.println("静态初始块");
    }

    public static void main(String[] args) {
        B b = new B();
//        b.show();
    }
}

/**
 * 静态语句块和静态变量一样在类第一次实例化时运行一次。
 * 存在继承的情况下，初始化顺序为：
    父类（静态变量、静态初始化块）
    子类（静态变量、静态初始化块）
    父类（变量、初始化块）
    父类（构造器）
    子类（变量、初始化块）
    子类（构造器）
 *
 */
class A {
    private final String a = "a";
    private final String aa = "aa";
    private static String staticField = "静态变量A";
    static {
//        System.out.println("A final 变量："+ aa); //无法找到，因为静态初始化块是先于图谱变量初始化加载的，故找不到
        System.out.println("静态初始块A");
    }
    public A() {
        System.out.println("A final 变量："+ aa);
    }
    private void show() {
       System.out.println(a);
   }
   public void showA() {
       show();
   }
}

final class B extends A {
    private final String b = "b";
    private static String staticField = "静态变量B";
    static {
        System.out.println("静态初始块B");
    }

    public B() {
        System.out.println("B final var:" + b);
    }

    public void show() {
        showA();
        System.out.println(b);
    }
}
