package com.qsl.seckill.service.impl;

import com.qsl.seckill.dao.GoodsDao;
import com.qsl.seckill.domain.Goods;
import com.qsl.seckill.domain.SeckillGoods;
import com.qsl.seckill.service.GoodsService;
import com.qsl.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author DanielQSL
 * @date 2018-10-11
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    GoodsDao goodsDao;

    @Override
    public List<GoodsVo> listGoodsVo() {
        return goodsDao.listGoodsVo();
    }

    @Override
    public GoodsVo getGoodsVoByGoodsId(long goodsId) {
        return goodsDao.getGoodsVoByGoodsId(goodsId);
    }

    @Override
    public void reduceStock(GoodsVo goods) {
        SeckillGoods updateGood = new SeckillGoods();
        updateGood.setGoodsId(goods.getId());
        goodsDao.reduceStock(updateGood);
    }
}
