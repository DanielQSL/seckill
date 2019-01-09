package com.qsl.seckill.rabbitmq;

import com.qsl.seckill.domain.SeckillUser;
import lombok.Getter;
import lombok.Setter;

/**
 * 秒杀对象信息
 * @author DanielQSL
 * @date 2019/1/9
 */
@Getter
@Setter
public class SeckillMessage {

    private SeckillUser user;

    private long goodsId;
}
