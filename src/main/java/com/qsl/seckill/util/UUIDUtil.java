package com.qsl.seckill.util;

import java.util.UUID;

/**
 * @author DanielQSL
 * @date 2018-10-09
 */
public class UUIDUtil {

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
