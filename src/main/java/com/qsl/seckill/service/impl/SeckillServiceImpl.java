package com.qsl.seckill.service.impl;

import com.qsl.seckill.common.ResponseCode;
import com.qsl.seckill.common.ServerResponse;
import com.qsl.seckill.domain.Goods;
import com.qsl.seckill.domain.OrderInfo;
import com.qsl.seckill.domain.SeckillUser;
import com.qsl.seckill.service.GoodsService;
import com.qsl.seckill.service.OrderService;
import com.qsl.seckill.service.SeckillService;
import com.qsl.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author DanielQSL
 * @date 2018/12/21
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Override
    @Transactional
    public ServerResponse<OrderInfo> seckill(SeckillUser user, GoodsVo goods) {
        //减库存，下订单，写入秒杀订单
        //1.减库存
        int reduceResult = goodsService.reduceStock(goods);
        if (reduceResult != 1) {
            return ServerResponse.createByErrorMessage(ResponseCode.SECKILL_OVER.getDesc());
        }
        //2.创建订单
        OrderInfo order = orderService.createOrder(user, goods);

        return ServerResponse.createBySuccess(order);
    }
}
