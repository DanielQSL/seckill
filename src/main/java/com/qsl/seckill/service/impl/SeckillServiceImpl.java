package com.qsl.seckill.service.impl;

import com.qsl.seckill.common.ResponseCode;
import com.qsl.seckill.common.ServerResponse;
import com.qsl.seckill.domain.Goods;
import com.qsl.seckill.domain.OrderInfo;
import com.qsl.seckill.domain.SeckillOrder;
import com.qsl.seckill.domain.SeckillUser;
import com.qsl.seckill.redis.RedisService;
import com.qsl.seckill.redis.SeckillKey;
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

    @Autowired
    RedisService redisService;

    @Override
    @Transactional
    public ServerResponse<OrderInfo> seckill(SeckillUser user, GoodsVo goods) {
        //1.减库存
        boolean reduceSuccess = goodsService.reduceStock(goods);
        //由于多线程的原因，此处需要判断是否更新成功
        if (!reduceSuccess) {
            setGoodsOver(goods.getId());
            return ServerResponse.createByErrorMessage(ResponseCode.SECKILL_OVER.getDesc());
        }
        //2.创建订单
        OrderInfo order = orderService.createOrder(user, goods);

        return ServerResponse.createBySuccess(order);
    }

    /**
     * 获取秒杀结果
     * @param userId
     * @param goodsId
     * @return
     */
    @Override
    public long getSeckillResult(Long userId, long goodsId) {
        SeckillOrder order = orderService.getSeckillOrderByUserIdAndGoodsId(userId, goodsId);
        if (order != null) {
            //秒杀成功
            return order.getOrderId();
        } else {
            boolean isOver = getGoodsOver(goodsId);
            if (isOver) {
                return 1;
            } else {
                return 0;
            }
        }

    }

    /**
     * 检查商品是否卖完
     * @param goodsId
     * @return
     */
    private boolean getGoodsOver(long goodsId) {
        return redisService.exists(SeckillKey.isGoodsOver, "" + goodsId);
    }

    /**
     * 设置商品已卖完
     * @param goodsId
     */
    private void setGoodsOver(Long goodsId) {
        redisService.set(SeckillKey.isGoodsOver, "" + goodsId, true);
    }
}
