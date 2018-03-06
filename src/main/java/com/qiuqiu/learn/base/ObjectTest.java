package com.qiuqiu.learn.base;

import org.apache.commons.lang.SerializationUtils;

import java.io.Serializable;

/**
 * Java 中有三个访问权限修饰符：private、protected 以及 public，如果不加访问修饰符，表示包级可见。
 */
public class ObjectTest {
    public static void main(String[] args) {
        Object object = new Object();
        System.out.println(object.getClass());
        System.out.println(object.equals(new Object()));
        System.out.println(object);
        System.out.println(object.getClass());
        System.out.println(object.getClass());
        System.out.println(object.getClass());

        ClassB b = new ClassB();
        b.show();

    }
}
abstract class ClassA implements Cloneable, Serializable{
    private Object object;
    private final String a = "a";
    private final String aa = "aa";
    private static String staticField = "静态变量A";
    static {
//        System.out.println("A final 变量："+ aa); //无法找到，因为静态初始化块是先于图谱变量初始化加载的，故找不到
        System.out.println("静态初始块A");
    }
    public ClassA() {
        System.out.println("A final 变量："+ aa);
    }
    private void show() {
        System.out.println(a);
    }

    protected abstract void method();

    public void showA() {
        System.out.println(this.hashCode());
        Object o = SerializationUtils.clone(this);
        System.out.println(o.hashCode() + o.getClass().toString());
        Object o2 = null, o3 = null;
        try {
            o2 = this.clone();
            o3 = this.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        System.out.println(o2.hashCode() + o2.getClass().toString());
        System.out.println(o3 == o2);
        System.out.println(o.equals(o2));
        System.out.println(o3.equals(o2));
        show();
    }
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

final class ClassB extends ClassA implements BInterface, AInterface{
    private final String b = "b";
    private static String staticField = "静态变量B";
    static {
        System.out.println("静态初始块B");
    }

    public ClassB() {
        System.out.println("B final var:" + b);
    }

    public String show() {
        showA();
        System.out.println(b);
        return "";
    }

    protected void method() {

    }

}

interface AInterface {
    String show();
}
interface  BInterface {
    String show();
}