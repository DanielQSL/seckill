package com.qsl.seckill.controller;

import com.qsl.seckill.common.ResponseCode;
import com.qsl.seckill.common.ServerResponse;
import com.qsl.seckill.domain.OrderInfo;
import com.qsl.seckill.domain.SeckillUser;
import com.qsl.seckill.redis.RedisService;
import com.qsl.seckill.service.GoodsService;
import com.qsl.seckill.service.OrderService;
import com.qsl.seckill.service.SeckillUserService;
import com.qsl.seckill.vo.GoodsVo;
import com.qsl.seckill.vo.OrderDetailVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author DanielQSL
 * @date 2019/1/9
 */
@Slf4j
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    SeckillUserService seckillUserService;

    @Autowired
    OrderService orderService;

    @Autowired
    GoodsService goodsService;

    @GetMapping("/detail")
    @ResponseBody
    public ServerResponse<OrderDetailVo> detail(Model model, SeckillUser user,
                                                @RequestParam("orderId") long orderId) {
        //todo 增加拦截器处理
        if (user == null) {
            return ServerResponse.createByErrorMessage(ResponseCode.SESSION_ERROR.getDesc());
        }
        OrderInfo order = orderService.getOrderById(orderId);
        if (order == null) {
            return ServerResponse.createByErrorMessage(ResponseCode.ORDER_NOT_EXIST.getDesc());
        }
        long goodsId = order.getGoodsId();
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        OrderDetailVo vo = new OrderDetailVo();
        vo.setGoods(goods);
        vo.setOrder(order);
        return ServerResponse.createBySuccess(vo);
    }

}
