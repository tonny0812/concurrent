package com.qiuqiu.learn.serializable.file;

import java.io.Serializable;

public class Box implements Serializable {
    private int width;
    private int height;
    transient private String name = "defaultBox";

    public Box(String name, int width, int height) {
        this.name = name;
        this.width = width;
        this.height = height;
    }

    @Override
    public String toString() {
        return "Child{" +
                "width=" + width +
                ", height=" + height +
                ", name=" + name +
                '}';
    }
}
