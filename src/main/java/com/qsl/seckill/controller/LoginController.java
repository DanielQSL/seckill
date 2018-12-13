package com.qsl.seckill.controller;

import com.qsl.seckill.common.ServerResponse;
import com.qsl.seckill.redis.RedisService;
import com.qsl.seckill.redis.SeckillUserKey;
import com.qsl.seckill.service.SeckillUserService;
import com.qsl.seckill.service.UserService;
import com.qsl.seckill.util.CookieUtils;
import com.qsl.seckill.util.UUIDUtil;
import com.qsl.seckill.util.ValidatorUtil;
import com.qsl.seckill.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author DanielQSL
 * @date 2018-10-08
 */
@Slf4j
@Controller
@RequestMapping("/login")
public class LoginController {

    private static final String COOKIE_NAME_TOKEN = "user_token";

    @Autowired
    SeckillUserService seckillUserService;

    @Autowired
    RedisService redisService;

    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public ServerResponse<Boolean> doLogin(@Valid LoginVo loginVo,
                                           HttpServletRequest request, HttpServletResponse response) {
        log.info(loginVo.toString());
//        //参数校验
//        String password = loginVo.getPassword();
//        String mobile = loginVo.getMobile();
//        if (StringUtils.isEmpty(password)) {
//            return ServerResponse.createByErrorMessage("密码不能为空");
//        }
//        if (StringUtils.isEmpty(mobile)) {
//            return ServerResponse.createByErrorMessage("手机号不能为空");
//        }
//        if (!ValidatorUtil.isMobile(mobile)) {
//            return ServerResponse.createByErrorMessage("手机号错误");
//        }
        ServerResponse result = seckillUserService.login(loginVo);
        //生成cookie
        String token = UUIDUtil.uuid();
        redisService.set(SeckillUserKey.token, token, result.getData());
        CookieUtils.setCookie(request, response, COOKIE_NAME_TOKEN, token);
        //登录
        return result;
    }
}
