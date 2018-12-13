package com.qsl.seckill.controller;

import com.qsl.seckill.domain.SeckillUser;
import com.qsl.seckill.redis.GoodsKey;
import com.qsl.seckill.redis.RedisService;
import com.qsl.seckill.service.GoodsService;
import com.qsl.seckill.service.SeckillUserService;
import com.qsl.seckill.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import java.util.List;

/**
 * @author DanielQSL
 * @date 2018-10-08
 */
@Slf4j
@Controller
@RequestMapping("/goods")
public class GoodsController {

    public static final String COOKIE_NAME_TOKEN = "user_token";

    @Autowired
    SeckillUserService seckillUserService;

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @RequestMapping(value = "/to_list", produces = "text/html")
    @ResponseBody
    public String toLogin(Model model,
//                          @CookieValue(value = COOKIE_NAME_TOKEN, required = false) String cookieToken,
//                          @RequestParam(value = COOKIE_NAME_TOKEN, required = false) String paramToken
                          SeckillUser user) {
//        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
//            return "login";
//        }
//        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
//        SeckillUser user = seckillUserService.getByToken(token);
        model.addAttribute("user", user);
        //查询商品列表
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);

        //return "goods_list";
        String html = redisService.get(GoodsKey.getGoodList, "", String.class);
        if (StringUtils.isNotEmpty(html)) {
            return html;
        }
//
//        //手动渲染
//        thymeleafViewResolver.getTemplateEngine().process("goods_list","44444" );
//        if (StringUtils.isNotEmpty(html)) {
//            redisService.set(GoodsKey.getGoodList, "", html);
//        }

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        })

        return null;
    }

    @RequestMapping("/to_detail/{goodsId}")
    public String toDetail(Model model, SeckillUser user,
                           @PathVariable("goodsId") long goodsId) {
        model.addAttribute("user", user);
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods", goods);

        //秒杀时间
        long startDate = goods.getStartDate().getTime();
        long endDate = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int seckillStatus = 0;
        int remainSeconds = 0;

        if (now < startDate) {//秒杀还没开始，倒计时
            seckillStatus = 0;
            remainSeconds = (int) ((startDate - now) / 1000);
        } else if (now > endDate) {//秒杀以结束
            seckillStatus = 2;
            remainSeconds = -1;
        } else {//秒杀进行中
            seckillStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("seckillStatus", seckillStatus);
        model.addAttribute("remainSeconds", remainSeconds);

        return "goods_detail";
    }

}
