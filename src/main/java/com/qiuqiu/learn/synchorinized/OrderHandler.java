package com.qiuqiu.learn.synchorinized;

public class OrderHandler {
    /*初始某商品库存量*/
    Integer StockSomeGoodsNum=200;
    /*用户下单*/
    public void Produce(int n){
        /*step1:判断可用库存操作*/
        synchronized(StockSomeGoodsNum) {
            if((StockSomeGoodsNum-n)>=0){
                /*为了更好体现线程间的竞争，让进程休眠一下*/
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                /*step2:执行减少库存操作*/
                StockSomeGoodsNum-=n;
                System.out.println("用户" + Thread.currentThread().getName()
                        + "成功购买商品：" + String.valueOf(n)+"个，库存剩余"+StockSomeGoodsNum+"个");
            }else{
                System.out.println("用户" + Thread.currentThread().getName()
                        + "下单失败，库存不足" + String.valueOf(n)+"个，库存剩余"+StockSomeGoodsNum+"个");
            }
        }
    }
    /*用户取消订单*/
    public void Cancel(int n){
        synchronized(StockSomeGoodsNum) {
            StockSomeGoodsNum+=n;
            System.out.println("用户" + Thread.currentThread().getName()
                    + "取消购买商品：" + String.valueOf(n)+"个，库存剩余"+StockSomeGoodsNum+"个");
        }
    }

    public static void main(String[] args) {
        final OrderHandler orderHandler=new OrderHandler();
        /*开启10个线程，模拟10个用户进行下单操作*/
        for(int i=0;i<10;i++){
            new Thread(new Runnable() {
                public void run() {
                    /*每个人购买商品数量为25个*/
                    orderHandler.Produce(75);
                }
            }).start();
        }
        /*开启5个线程，模拟5个用户在进行取消订单操作*/
        for(int i=0;i<5;i++){
            new Thread(new Runnable() {
                public void run() {
                    /*每个取消订单中包含的商品数为3个*/
                    orderHandler.Cancel(3);
                }
            }).start();
        }
    }

}
