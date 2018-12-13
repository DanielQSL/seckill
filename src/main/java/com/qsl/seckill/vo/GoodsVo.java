package com.qsl.seckill.vo;

import com.qsl.seckill.domain.Goods;
import lombok.Data;

import java.util.Date;

/**
 * @author DanielQSL
 * @date 2018-10-11
 */
@Data
public class GoodsVo extends Goods {
    private Double seckillPrice;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
}
