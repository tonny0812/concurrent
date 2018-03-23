package com.qiuqiu.learn.serializable.socket;

import java.io.Serializable;

public class Student implements Serializable {
    // serialVersionUID 避免向后兼容性问题
    private static final long serialVersionUID = 8683452581334592189L;
    private String name;
    private int age;
    private int score;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    @Override
    public String toString() {
        return "name:" + name + " age:" + age + " score:" + score;
    }
}
