package com.qsl.seckill.service;

import com.qsl.seckill.vo.GoodsVo;

import java.util.List;

/**
 * @author DanielQSL
 * @date 2018-10-11
 */
public interface GoodsService {

    List<GoodsVo> listGoodsVo();

    GoodsVo getGoodsVoByGoodsId(long goodsId);
}
