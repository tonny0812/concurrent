package com.qiuqiu.learn.generic;

import java.util.ArrayList;
import java.util.List;

/**
 * 要add元素应该怎么做呢？可以使用<? super T>
 *
 * 这样我们可以往容器里面添加元素了，但是使用super的坏处是以后不能get容器里面的元素了，
 * 原因很简单，我们继续从编译器的角度考虑这个问题，对于List<? super Apple> list，它可以有下面几种含义：
 * List<? super Apple> list = new ArrayList<Apple>();
 * List<? super Apple> list = new ArrayList<Fruit>();
 * List<? super Apple> list = new ArrayList<Object>();
 * 当我们尝试通过list来get一个Apple的时候，可能会get得到一个Fruit，这个Fruit可以是Orange等其他类型的Fruit。
 * 根据上面的例子，我们可以总结出一条规律，”Producer Extends, Consumer Super”：
 *
 * “Producer Extends” - 如果你需要一个只读List，用它来produce T，那么使用? extends T。
 * “Consumer Super” - 如果你需要一个只写List，用它来consume T，那么使用? super T。
 * 如果需要同时读取以及写入，那么我们就不能使用通配符了。
 */
public class GenericWriting {
    static List<Apple> apples = new ArrayList<Apple>();
    static List<Fruit> fruit = new ArrayList<Fruit>();
    static <T> void writeExact(List<T> list, T item) {
        list.add(item);
    }
    static void f1() {
        writeExact(apples, new Apple());
        writeExact(fruit, new Apple());
    }
    static <T> void writeWithWildcard(List<? super T> list, T item) {
        list.add(item);
    }
    static void f2() {
        writeWithWildcard(apples, new Apple());
        writeWithWildcard(fruit, new Apple());
    }
    public static void main(String[] args) {
        f1(); f2();
    }
}

/**
 * java集合类中将二者结合起来使用
 */
class Collections {
    public static <T> void copy(List<? super T> dest, List<? extends T> src) {
        for (int i=0; i<src.size(); i++)
            dest.set(i, src.get(i));
    }
}
