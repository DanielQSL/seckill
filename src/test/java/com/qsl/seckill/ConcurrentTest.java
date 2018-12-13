package com.qsl.seckill;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author DanielQSL
 * @date 2018/12/11
 */
public class ConcurrentTest {
    public static void main(String[] args) {
//        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
//
//        CopyOnWriteArrayList<String> copy=new CopyOnWriteArrayList<>();

        AtomicLong count=new AtomicLong(1);
        boolean isSuccess = count.compareAndSet(1, 2);
        System.out.println(isSuccess);
        System.out.println(count.get());
    }
}
