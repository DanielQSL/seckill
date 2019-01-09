package com.qsl.seckill.service.impl;

import com.qsl.seckill.dao.OrderDao;
import com.qsl.seckill.domain.OrderInfo;
import com.qsl.seckill.domain.SeckillOrder;
import com.qsl.seckill.domain.SeckillUser;
import com.qsl.seckill.redis.OrderKey;
import com.qsl.seckill.redis.RedisService;
import com.qsl.seckill.service.OrderService;
import com.qsl.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author DanielQSL
 * @date 2018/12/21
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderDao;

    @Autowired
    RedisService redisService;

    @Override
    public SeckillOrder getSeckillOrderByUserIdAndGoodsId(long userId, long goodsId) {
        //return orderDao.getSeckillOrderByUserIdAndGoodsId(userId, goodsId);
        return redisService.get(OrderKey.getSeckillOrderByUidGid, "_" + userId + "_" + goodsId, SeckillOrder.class);
    }

    /**
     * 根据id获取订单信息
     *
     * @param orderId
     * @return
     */
    @Override
    public OrderInfo getOrderById(long orderId) {
        return orderDao.getOrderById(orderId);
    }

    /**
     * 创建订单
     *
     * @param user
     * @param goods
     * @return
     */
    @Transactional
    @Override
    public OrderInfo createOrder(SeckillUser user, GoodsVo goods) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsPrice(goods.getSeckillPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getId());

        orderDao.insert(orderInfo);

        SeckillOrder order = new SeckillOrder();
        order.setGoodsId(goods.getId());
        order.setOrderId(orderInfo.getId());
        order.setUserId(user.getId());

        orderDao.insertSeckillOrder(order);

        redisService.set(OrderKey.getSeckillOrderByUidGid, "_" + user.getId() + "_" + goods.getId(), order);

        return orderInfo;
    }

}
