package com.qsl.seckill.controller;

import com.qsl.seckill.common.ResponseCode;
import com.qsl.seckill.common.ServerResponse;
import com.qsl.seckill.domain.OrderInfo;
import com.qsl.seckill.domain.SeckillOrder;
import com.qsl.seckill.domain.SeckillUser;
import com.qsl.seckill.rabbitmq.MQSender;
import com.qsl.seckill.rabbitmq.SeckillMessage;
import com.qsl.seckill.redis.GoodsKey;
import com.qsl.seckill.redis.RedisService;
import com.qsl.seckill.service.GoodsService;
import com.qsl.seckill.service.OrderService;
import com.qsl.seckill.service.SeckillService;
import com.qsl.seckill.service.UserService;
import com.qsl.seckill.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 秒杀业务
 *
 * @author DanielQSL
 * @date 2018-12-21
 */
@Slf4j
@Controller
@RequestMapping("/seckill")
public class SeckillController implements InitializingBean {

    @Autowired
    UserService userService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    RedisService redisService;

    @Autowired
    SeckillService seckillService;

    @Autowired
    MQSender sender;

    private Map<Long ,Boolean> localOverMap=new HashMap<>();

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
    public ServerResponse<Integer> seckill(Model model, SeckillUser user, @RequestParam("goodsId") long goodsId) {
        model.addAttribute("user", user);
        if (user == null) {
            return ServerResponse.createByErrorMessage(ResponseCode.SESSION_ERROR.getDesc());
        }

        //利用内存标记，减少redis访问
        Boolean over = localOverMap.get(goodsId);
        if (over){
            return ServerResponse.createByErrorMessage(ResponseCode.SECKILL_SELLEDOUT.getDesc());
        }

        //1.预减库存
        long stock = redisService.decr(GoodsKey.getSeckillGoodsStock, "" + goodsId);
        if (stock < 0) {
            return ServerResponse.createByErrorMessage(ResponseCode.SECKILL_SELLEDOUT.getDesc());
        }

        //2.判断是否已经秒杀到了
        SeckillOrder order = orderService.getSeckillOrderByUserIdAndGoodsId(user.getId(), goodsId);
        if (order != null) {
            return ServerResponse.createByErrorMessage(ResponseCode.REPEATE_SECKILL.getDesc());
        }

        //3.入队
        SeckillMessage message = new SeckillMessage();
        message.setUser(user);
        message.setGoodsId(goodsId);
        sender.sendSeckillMessage(message);

        return ServerResponse.createBySuccess(0);//排队中

        /*//判断库存
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
        return seckillService.seckill(user, goods);*/
    }

    /**
     * 查询秒杀结果
     * @param model
     * @param user
     * @param goodsId
     * @return orderId:成功 -1:秒杀失败 0：排队中
     */
    @RequestMapping(value = "/result", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<Long> seckillResult(Model model, SeckillUser user, @RequestParam("goodsId") long goodsId) {
        model.addAttribute("user", user);
        if (user == null) {
            return ServerResponse.createByErrorMessage(ResponseCode.SESSION_ERROR.getDesc());
        }

        long result = seckillService.getSeckillResult(user.getId(), goodsId);

        return ServerResponse.createBySuccess(result);
    }

    /**
     * 系统初始化
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        if (goodsList == null) {
            return;
        }
        for (GoodsVo goods : goodsList) {
            redisService.set(GoodsKey.getSeckillGoodsStock, "" + goods.getId(), goods.getGoodsStock());
            localOverMap.put(goods.getId(), false);
        }
    }
}
