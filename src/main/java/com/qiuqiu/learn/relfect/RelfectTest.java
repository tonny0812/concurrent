package com.qiuqiu.learn.relfect;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 如果不需要动态地创建一个对象，那么就不需要用反射
 * 反射调用方法时可以忽略权限检查，因此可能会破坏封装性而导致安全问题
 */
public class RelfectTest {
    public static void main(String[] args) {
        Class<?> klass = int.class;
        Class<?> classInt = Integer.TYPE;
        StringBuilder str = new StringBuilder("123");
        Class<?> strClass = str.getClass();
        strClass.getPackage();

        //获取String所对应的Class对象
        Class<?> c = String.class;
        try {
            Object str1 = c.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        //获取String类带一个String参数的构造器
        Constructor constructor = null;
        try {
            constructor = c.getConstructor(String.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        //根据构造器创建实例
        Object obj = null;
        try {
            obj = constructor.newInstance("23333");
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println(obj);

        try {
            testClassMethod();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        try {
            testInvokeMethod();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        try {
            testArray();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取方法
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public static void testClassMethod() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class<?> c = methodClass.class;
        Object object = c.newInstance();
        Method[] methods = c.getMethods();
        Method[] declaredMethods = c.getDeclaredMethods();
        //获取methodClass类的add方法
        Method method = c.getMethod("add", int.class, int.class);
        System.out.println(method);
        //getMethods()方法获取的所有方法
        System.out.println("getMethods获取的方法：");
        for(Method m:methods)
            System.out.println(m);
        //getDeclaredMethods()方法获取的所有方法
        System.out.println("getDeclaredMethods获取的方法：");
        for(Method m:declaredMethods)
            System.out.println(m);

        //获取类的成员变量（字段）信息
        try {
            System.out.println(c.getDeclaredField("j"));
            System.out.println(c.getField("name"));
            System.out.println(c.getField("test"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

    }

    /**
     * 当我们从类中获取了一个方法后，我们就可以用invoke()方法来调用这个方法。invoke方法的原型为
     * public Object invoke(Object obj, Object... args)
            throws IllegalAccessException, IllegalArgumentException,
                     InvocationTargetException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public static void testInvokeMethod() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class<?> klass = methodClass.class;
        //创建methodClass的实例
        Object obj = klass.newInstance();
        //获取methodClass类的add方法
        Method method = klass.getMethod("add",int.class,int.class);
        //调用method对应的方法 => add(1,4)
        Object result = method.invoke(obj,1,4);
        System.out.println(result);
    }

    /**
     * 利用反射创建数组
     * @throws ClassNotFoundException
     */
    public static void testArray() throws ClassNotFoundException {
        Class<?> cls = Class.forName("java.lang.String");
        Object array = Array.newInstance(cls,25);
        //往数组里添加内容
        Array.set(array,0,"hello");
        Array.set(array,1,"Java");
        Array.set(array,2,"fuck");
        Array.set(array,3,"Scala");
        Array.set(array,4,"Clojure");
        //获取某一项的内容
        System.out.println(Array.get(array,3));
    }
}

class superClass {
    public String test;
}

class methodClass extends superClass{

    public String name;

    private  int j;

    public methodClass() {

    }

    public methodClass(int i, int j) {

    }
    public final int fuck = 3;
    public int add(int a,int b) {
        return a+b;
    }
    public int sub(int a,int b) {
        return a+b;
    }

    private void test() {

    }
}

