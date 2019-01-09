package com.qsl.seckill.service;

import com.qsl.seckill.common.ServerResponse;
import com.qsl.seckill.domain.OrderInfo;
import com.qsl.seckill.domain.SeckillUser;
import com.qsl.seckill.vo.GoodsVo;
import org.springframework.stereotype.Service;

/**
 * @author DanielQSL
 * @date 2018/12/21
 */
public interface SeckillService {

    ServerResponse<OrderInfo> seckill(SeckillUser user, GoodsVo goods);

    long getSeckillResult(Long userId, long goodsId);
}
