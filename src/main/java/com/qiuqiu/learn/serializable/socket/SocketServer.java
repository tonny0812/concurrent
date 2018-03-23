package com.qiuqiu.learn.serializable.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    public static void openObjectServer(){
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(1111);
            while(true){
                final Socket socket = ss.accept();
                new Runnable(){
                    public void run() {
                        try {
                            InputStream is = socket.getInputStream();
                            OutputStream os = socket.getOutputStream();
                            os.write("欢迎连接 服务器 一号!".getBytes());
                            ObjectInputStream ois = new ObjectInputStream(is);
                            Object object = ois.readObject();
                            //打印对象

                            System.out.println(object);
                            //关闭socket
                            socket.close();
                        }catch(Exception e){
                            e.printStackTrace();


                        }finally{
                            if(socket != null )
                                try {
                                    socket.close();
                                } catch (IOException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                        }
                    }}.run();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            System.out.println("服务器关闭连接！");
            try {
                if(ss != null)
                    ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SocketServer.openObjectServer();
    }
}
