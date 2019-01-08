package com.qsl.seckill.service;

import com.qsl.seckill.domain.OrderInfo;
import com.qsl.seckill.domain.SeckillUser;
import com.qsl.seckill.vo.GoodsVo;
import org.springframework.stereotype.Service;

/**
 * @author DanielQSL
 * @date 2018/12/21
 */
public interface SeckillService {

    OrderInfo seckill(SeckillUser user, GoodsVo goods);
}
