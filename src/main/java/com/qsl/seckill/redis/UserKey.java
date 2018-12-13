package com.qsl.seckill.redis;

/**
 * @author DanielQSL
 * @date 2018-10-08
 */
public class UserKey extends BasePrefix {

    private UserKey(String prefix) {
        super(prefix);
    }

    public static UserKey getById = new UserKey("id");
    public static UserKey getByName = new UserKey("name");
}
