package com.qiuqiu.learn.serializable.file;

import java.io.*;

public class SerializableTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        File file = new File("box.out");

        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream out = new ObjectOutputStream(fos);
        Box oldBox = new Box("testBox",10,20);
        out.writeObject(oldBox);
        out.close();

        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream in = new ObjectInputStream(fis);
        Box newBox = (Box)in.readObject();
        in.close();
        System.out.println(newBox.toString());
    }
}
