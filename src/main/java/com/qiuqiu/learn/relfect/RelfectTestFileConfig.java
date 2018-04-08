package com.qiuqiu.learn.relfect;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

public class RelfectTestFileConfig {
    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //从spring.txt中获取类名称和方法名称
        File springConfigFile = new File("C:\\Users\\qiuqiu\\IdeaProjects\\concurrent\\src\\main\\java\\com\\qiuqiu\\learn\\relfect\\spring.txt");
        Properties springConfig = new Properties();
        springConfig.load(new FileInputStream(springConfigFile));
        String className = (String) springConfig.get("class");
        String methodName = (String) springConfig.get("method");

        //根据类名称获取类对象
        Class clazz = Class.forName(className);
        //根据方法名称，获取方法对象
        Method m = clazz.getMethod(methodName);
        //获取构造器
        Constructor c = clazz.getConstructor();
        //根据构造器，实例化出对象
        Object service = c.newInstance();
        //调用对象的指定方法
        m.invoke(service);

    }
}

interface service {
    public void doSomething();
}


