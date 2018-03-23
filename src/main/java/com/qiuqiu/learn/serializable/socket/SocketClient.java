package com.qiuqiu.learn.serializable.socket;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class SocketClient {

    public static void sendMessageToServer(String name, int age, int score) {
        Socket socket = null;
        try{
            socket = new Socket(InetAddress.getByName("127.0.0.1"),1111);
            OutputStream os = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            Student student = new Student();
            student.setAge(age);
            student.setName(name);
            student.setScore(score);
            oos.writeObject(student);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                if(socket != null)
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        for(int i=0;i<10000;i++) {
            String name = "name-" + i;
            sendMessageToServer(name, 20, 100);
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
