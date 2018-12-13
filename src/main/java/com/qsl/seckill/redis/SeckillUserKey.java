package com.qsl.seckill.redis;

/**
 * @author DanielQSL
 * @date 2018-10-09
 */
public class SeckillUserKey extends BasePrefix {

    public static final int TOKEN_EXPIRE = 3600 * 24 * 2;

    public static SeckillUserKey token = new SeckillUserKey(TOKEN_EXPIRE, "user_token");

    public SeckillUserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

}
