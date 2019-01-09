package com.qsl.seckill.rabbitmq;

import com.qsl.seckill.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 发送者
 *
 * @author DanielQSL
 * @date 2019/1/9
 */
@Service
@Slf4j
public class MQSender {

    @Autowired
    AmqpTemplate amqpTemplate;

//    public void send(Object message) {
//        String msg = RedisService.beanToString(message);
//        log.info("prepare send message:{}", msg);
//        amqpTemplate.convertAndSend(MQConfig.QUEUE, msg);
//    }
//
//    public void sendTopic(Object message) {
//        String msg = RedisService.beanToString(message);
//        log.info("prepare send topic message:{}", msg);
//        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "topic.key1", msg + "_1");
//        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "topic.nihao", msg + "_2");
//    }
//
//    public void sendFanout(Object message) {
//        String msg = RedisService.beanToString(message);
//        log.info("prepare send fanout message:{}", msg);
//        amqpTemplate.convertAndSend(MQConfig.FANOUT_EXCHANGE,"", msg);
//    }

    /**
     * 发送秒杀信息
     * @param message
     */
    public void sendSeckillMessage(SeckillMessage message) {
        String msg = RedisService.beanToString(message);
        log.info("send SeckillMessage:{}", msg);
        amqpTemplate.convertAndSend(MQConfig.SECKILL_QUEUE, msg);
    }
}
