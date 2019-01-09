package com.qsl.seckill.service;

import com.qsl.seckill.domain.OrderInfo;
import com.qsl.seckill.domain.SeckillOrder;
import com.qsl.seckill.domain.SeckillUser;
import com.qsl.seckill.vo.GoodsVo;

/**
 * @author DanielQSL
 * @date 2018/12/21
 */
public interface OrderService {

    SeckillOrder getSeckillOrderByUserIdAndGoodsId(long userId, long goodsId);

    OrderInfo createOrder(SeckillUser user, GoodsVo goods);

    OrderInfo getOrderById(long orderId);
}
