package com.qsl.seckill.vo;

import com.qsl.seckill.domain.SeckillUser;

/**
 * @author DanielQSL
 * @date 2019/1/9
 */
public class GoodsDetailVo  {

    private GoodsVo goodsVo;

    private int seckillStatus;

    private int remainSeconds;

    private SeckillUser seckillUser;

    public GoodsVo getGoodsVo() {
        return goodsVo;
    }

    public void setGoodsVo(GoodsVo goodsVo) {
        this.goodsVo = goodsVo;
    }

    public int getSeckillStatus() {
        return seckillStatus;
    }

    public void setSeckillStatus(int seckillStatus) {
        this.seckillStatus = seckillStatus;
    }

    public int getRemainSeconds() {
        return remainSeconds;
    }

    public void setRemainSeconds(int remainSeconds) {
        this.remainSeconds = remainSeconds;
    }

    public SeckillUser getSeckillUser() {
        return seckillUser;
    }

    public void setSeckillUser(SeckillUser seckillUser) {
        this.seckillUser = seckillUser;
    }
}
