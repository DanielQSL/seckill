package com.qsl.seckill.controller;

import com.qsl.seckill.common.ResponseCode;
import com.qsl.seckill.common.ServerResponse;
import com.qsl.seckill.domain.OrderInfo;
import com.qsl.seckill.domain.SeckillOrder;
import com.qsl.seckill.domain.SeckillUser;
import com.qsl.seckill.redis.RedisService;
import com.qsl.seckill.service.GoodsService;
import com.qsl.seckill.service.OrderService;
import com.qsl.seckill.service.SeckillService;
import com.qsl.seckill.service.UserService;
import com.qsl.seckill.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 秒杀业务
 *
 * @author DanielQSL
 * @date 2018-12-21
 */
@Slf4j
@Controller
@RequestMapping("/seckill")
public class SeckillController {

    @Autowired
    UserService userService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    SeckillService seckillService;

    /**
     * 秒杀
     * qps:76.7/sec  请求数：1000*8
     *
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/do_seckill", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<OrderInfo> seckill(Model model, SeckillUser user, @RequestParam("goodsId") long goodsId) {
        model.addAttribute("user", user);
        if (user == null) {
            return ServerResponse.createByErrorMessage(ResponseCode.SESSION_ERROR.getDesc());
        }
        //判断库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if (stock <= 0) {
            return ServerResponse.createByErrorMessage(ResponseCode.SECKILL_SELLEDOUT.getDesc());
        }
        //判断是否已经秒杀到了
        SeckillOrder order = orderService.getSeckillOrderByUserIdAndGoodsId(user.getId(), goodsId);
        if (order != null) {
            return ServerResponse.createByErrorMessage(ResponseCode.REPEATE_SECKILL.getDesc());
        }

        //减库存，下订单，写入秒杀订单
        return seckillService.seckill(user, goods);
    }

}
