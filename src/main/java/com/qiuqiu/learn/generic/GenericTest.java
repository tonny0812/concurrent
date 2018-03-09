package com.qiuqiu.learn.generic;

public class GenericTest {
    public static void main(String[] args) {
        Pair<Integer, String> p1 = new Pair<>(1, "apple");
        Pair<Integer, String> p2 = new Pair<>(2, "pear");
        boolean same = Util.<Integer, String>compare(p1, p2);
        boolean same2 = Util.compare(p1, p2);
        System.out.println(same);
        System.out.println(same2);
    }
}

class Util {
    public static <K, V> boolean compare(Pair<K, V> p1, Pair<K, V> p2) {
        return p1.getKey().equals(p2.getKey()) &&
                p1.getValue().equals(p2.getValue());
    }

    /**
     *  边界符
     *  做一个类似于下面这样的声明，这样就等于告诉编译器类型参数T代表的都是实现了Comparable接口的类，这样等于告诉编译器它们都至少实现了compareTo方法
     */
    public static <T extends Comparable<T>> int countGreaterThan(T[] anArray, T elem) {
        int count = 0;
        for (T e : anArray)
//            if( e > elem) // compiler error
            if (e.compareTo(elem) > 0)
                ++count;
        return count;
    }

    /**
     * 通配符
     * 那么现在Box<Number> n允许接受什么类型的参数？
     * 我们是否能够传入Box<Integer>或者Box<Double>呢？
     * 答案是否定的，虽然Integer和Double是Number的子类，
     * 但是在泛型中Box<Integer>或者Box<Double>与Box<Number>之间并没有任何的关系。
     * @param n
     */
    public static void boxTest(Box<Number> n) {
        /* ... */
    }
}

class Pair<K, V> {
    private K key;
    private V value;
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }
    public void setKey(K key) { this.key = key; }
    public void setValue(V value) { this.value = value; }
    public K getKey()   { return key; }
    public V getValue() { return value; }
}

class Box<T> {
    // T stands for "Type"
    private T t;
    public void set(T t) { this.t = t; }
    public T get() { return t; }
}

