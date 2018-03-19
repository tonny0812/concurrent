package com.qiuqiu.learn.hashmap;


public class HashCodeTest {

    private static final int MAXIMUM_CAPACITY = 1 << 30;

    public static void main(String[] args) {
        int tableLength = 20;
        for(int index=0;index<tableLength;index++) {
            String key = String.valueOf(index);
            int temp;
            int hash = (temp=key.hashCode()) ^ (temp>>>16);
            int _index = (tableLength - 1) & hash;
            System.out.println("字符串："+ key +" 的hashcode值："+key.hashCode() + " 对应hash表的下标是：" + _index);
        }
        System.out.println("Table容量："+tableSizeFor(20));
        System.out.println("Table容量："+tableResize(20));
    }

     /**
     * Returns a power of two size for the given target capacity.
      * JDK1.8
     */
    public static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    public static final  int tableResize(int initialCapacity) {
        // Find a power of 2 >= initialCapacity
        // 这里需要注意一下
        int capacity = 1;
        while (capacity < initialCapacity)
            capacity <<= 1;
        return capacity;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
