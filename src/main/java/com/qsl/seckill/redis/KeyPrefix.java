package com.qsl.seckill.redis;

/**
 * @author DanielQSL
 * @date 2018-10-08
 */
public interface KeyPrefix {

    int expireSeconds();

    String getPrefix();
}
