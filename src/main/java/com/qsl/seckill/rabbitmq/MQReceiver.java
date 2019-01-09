package com.qsl.seckill.rabbitmq;

import com.qsl.seckill.domain.SeckillOrder;
import com.qsl.seckill.domain.SeckillUser;
import com.qsl.seckill.redis.RedisService;
import com.qsl.seckill.service.GoodsService;
import com.qsl.seckill.service.OrderService;
import com.qsl.seckill.service.SeckillService;
import com.qsl.seckill.service.UserService;
import com.qsl.seckill.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 接收者
 *
 * @author DanielQSL
 * @date 2019/1/9
 */
@Service
@Slf4j
public class MQReceiver {
    @Autowired
    UserService userService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    RedisService redisService;

    @Autowired
    SeckillService seckillService;

    @RabbitListener(queues = MQConfig.SECKILL_QUEUE)
    public void receive(String message) {
        log.info("receive message:" + message);
        SeckillMessage sm = RedisService.stringToBean(message, SeckillMessage.class);
        SeckillUser user = sm.getUser();
        long goodsId = sm.getGoodsId();

        //判断库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if (stock <= 0) {
            return;
        }

        //判断是否已经秒杀到了
        SeckillOrder order = orderService.getSeckillOrderByUserIdAndGoodsId(user.getId(), goodsId);
        if (order != null) {
            return;
        }

        //减库存，下订单，写入秒杀订单
        seckillService.seckill(user, goods);
    }

//    @RabbitListener(queues = MQConfig.QUEUE)
//    public void receive(String message) {
//        log.info("receive message:" + message);
//    }
//
//    @RabbitListener(queues = MQConfig.TOPIC_QUEUE1)
//    public void receiveTopic1(String message) {
//        log.info("receive topic1 message:" + message);
//    }
//
//    @RabbitListener(queues = MQConfig.TOPIC_QUEUE2)
//    public void receiveTopic2(String message) {
//        log.info("receive topic2 message:" + message);
//    }
}
