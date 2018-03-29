package com.qiuqiu.learn.synchorinized;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 从demo中我们看到，我们将实现模拟多个线程对不同的账户的账户金额进行修改，
 * 然后重新保存。其中线程1、线程2是对账户100001进行操作，
 * 线程3、线程4对100002进行操作。它们在运行过程中只有线程1与线程2,线程3与线程4互斥，
 * 不同的账户同时操作是互不影响的，这样就能大大提高性
 */
public class LockTest2 {
    // 放并发锁的map
    public static Map<String, Lock> currentACCount = new ConcurrentHashMap<String, Lock>();
    // 放账户金额，本来应该放在数据库的，为了测试就放入map中
    public static Map<String, Long> accountMoney = new ConcurrentHashMap<String, Long>();

    public static void main(String[] argv) {
        new TestThread6("100001",100).start();
        new TestThread6("100001",10).start();
        new TestThread6("100002",100).start();
        new TestThread6("100002",-10).start();
    }

    /**
     * 获取锁
     *
     * @param key
     * @return
     */
    public static synchronized Lock getKey(String key) {
        Lock obj = currentACCount.get(key);
        if (obj == null) {
            obj = new ReentrantLock();
            currentACCount.put(key, obj);
        }
        System.out.println("获得key:" + key + ":" + obj);
        return obj;
    }

    /**
     * 修改账户金额
     *
     * @param account
     *            账户
     * @param money
     *            金额 （单位：分）
     */
    public static void updateAccountMoney(String account, Long money) {
        System.out.println("thread name：" + Thread.currentThread().getName()
                + " 账号：" + account + ",money=" + money);
        Lock lock = getKey(account);
        //当锁已经被其它线程获取到，该线程将会处于等待状态
        lock.lock();
        System.out.println("thread name：" + Thread.currentThread().getName()
                + " 账号：" + account + "获取到锁成功");
        long oriAmount = accountMoney.get(account)==null?0:accountMoney.get(account);
        System.out.println("thread name：" + Thread.currentThread().getName()
                + " 账号：" + account + " 账户原金额：oriAmount=" + oriAmount);
        long amount = oriAmount + money;
        System.out.println("thread name：" + Thread.currentThread().getName()
                + " 账号：" + account + " 更新后金额：amount=" + amount);
        accountMoney.put(account, amount);
        System.out.println("thread name：" + Thread.currentThread().getName()
                + " 账号：" + account + "释放锁");
        lock.unlock();
    }

}

// 测试线程
class TestThread6 extends Thread {
    private String account;
    private long money;

    public TestThread6(String account, long money) {
        super();
        this.account = account;
        this.money = money;
    }

    public void run() {
        LockTest2.updateAccountMoney(account, money);
    }
}
