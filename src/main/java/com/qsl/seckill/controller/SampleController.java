package com.qsl.seckill.controller;

import com.qsl.seckill.common.ServerResponse;
import com.qsl.seckill.domain.User;
import com.qsl.seckill.rabbitmq.MQSender;
import com.qsl.seckill.redis.RedisService;
import com.qsl.seckill.redis.UserKey;
import com.qsl.seckill.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author DanielQSL
 * @date 2018-09-29
 */
@Controller
@RequestMapping("/demo")
@Api("测试相关demo")
public class SampleController {

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    MQSender sender;

//    @RequestMapping("/mq/topic")
//    @ResponseBody
//    public ServerResponse<String> topic(Model model) {
//        sender.sendTopic("hello,rabbitmq");
//        return ServerResponse.createBySuccess();
//    }
//
//    @RequestMapping("/mq/fanout")
//    @ResponseBody
//    public ServerResponse<String> fanout(Model model) {
//        sender.sendFanout("hello,rabbitmq");
//        return ServerResponse.createBySuccess();
//    }
//
//    @RequestMapping("/mq")
//    @ResponseBody
//    public ServerResponse<String> mq(Model model) {
//        sender.send("hello,rabbitmq");
//        return ServerResponse.createBySuccess();
//    }

    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model) {
        model.addAttribute("name", "daniel");
        return "hello";
    }

    @ApiOperation("获取用户信息")
    @RequestMapping(value = "db/getUser",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<User> dbGet() {
        User user = userService.getById(1);
        return ServerResponse.createBySuccess(user);
    }

    @RequestMapping("redis/get")
    @ResponseBody
    public ServerResponse<User> redisGet() {
        User user = redisService.get(UserKey.getById, "100", User.class);
        return ServerResponse.createBySuccess(user);
    }

//    @RequestMapping("redis/set")
//    @ResponseBody
//    public ServerResponse<String> redisSet() {
//        User user = new User();
//        user.setId(1);
//        user.setName("ww");
//        redisService.set(UserKey.getById, "100", user);
//        return ServerResponse.createBySuccess();
//    }
}
