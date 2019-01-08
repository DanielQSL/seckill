package com.qsl.seckill.dao;

import com.qsl.seckill.domain.SeckillGoods;
import com.qsl.seckill.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author DanielQSL
 * @date 2018-10-11
 */
@Mapper
public interface GoodsDao {

    @Select("select g.*,sg.seckill_price,sg.stock_count,sg.start_date,sg.end_date from seckill_goods sg left join goods g on sg.goods_id = g.id")
    List<GoodsVo> listGoodsVo();

    @Select("select g.*,sg.seckill_price,sg.stock_count,sg.start_date,sg.end_date from seckill_goods sg left join goods g on sg.goods_id = g.id where g.id = #{goodsId}")
    GoodsVo getGoodsVoByGoodsId(@Param("goodsId") long goodsId);

    @Update("update seckill_goods set stock_count = stock_count - 1 where goods_id = #{goodsId}")
    int reduceStock(SeckillGoods updateGood);
}
