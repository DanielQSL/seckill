package com.qsl.seckill.common;

public enum ResponseCode {

    SUCCESS(0, "SUCCESS"),
    ERROR(1, "ERROR"),
    NEED_LOGIN(10, "NEED_LOGIN"),
    ILLEGAL_ARGUMENT(2, "ILLEGAL_ARGUMENT"),
    MOBILE_NOT_EXIST(50021, "手机号不存在"),
    SERVER_ERROR(-100, "服务端异常"),

    //用户模块
    SESSION_ERROR(500401, "用户信息没有"),

    //订单模块
    ORDER_NOT_EXIST(500601, "订单不存在"),

    //商品模块
    SECKILL_OVER(500501, "商品已经秒杀完毕"),
    REPEATE_SECKILL(500502, "不能重复秒杀"),
    SECKILL_SELLEDOUT(500503, "商品已经卖完");

    private final int code;
    private final String desc;

    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
