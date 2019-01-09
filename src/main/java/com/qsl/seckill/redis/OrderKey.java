package com.qsl.seckill.redis;

/**
 * 商品key
 *
 * @author DanielQSL
 * @date 2018-11-22
 */
public class OrderKey extends BasePrefix {

    private OrderKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static OrderKey getSeckillOrderByUidGid = new OrderKey(60*24,"SeckillOrder");
}
