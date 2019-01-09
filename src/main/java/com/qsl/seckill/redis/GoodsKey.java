package com.qsl.seckill.redis;

/**
 * 商品key
 *
 * @author DanielQSL
 * @date 2018-11-22
 */
public class GoodsKey extends BasePrefix {

    private GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static GoodsKey getGoodList = new GoodsKey(60,"goodList");
    public static GoodsKey getGoodDetail = new GoodsKey(60,"goodDetail");
    public static GoodsKey getSeckillGoodsStock = new GoodsKey(0,"goodStock");
}
