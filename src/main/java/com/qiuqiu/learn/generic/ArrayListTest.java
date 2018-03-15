package com.qiuqiu.learn.generic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayListTest {
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        for(int i=0;i<5000;i++) {
            list.add(String.valueOf(i));
        }
        /**
         * 会报ConcurrentModificationException异常
         */
//        for(String str : list) {
//            if(str.equals("7"))
//                list.remove(str);
//        }

        /**
         * 使用迭代器删除不会引发一场问题
         */
        Iterator<String> iterator = list.iterator();
        while(iterator.hasNext()) {
            if("7".equals(iterator.next())) {
                iterator.remove();
            } else {
                System.out.println("args = [" + iterator.next() + "]");
            }
        }
    }
}
