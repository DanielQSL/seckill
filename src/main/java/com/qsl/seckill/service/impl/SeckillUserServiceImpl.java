package com.qsl.seckill.service.impl;

import com.qsl.seckill.common.ResponseCode;
import com.qsl.seckill.common.ServerResponse;
import com.qsl.seckill.dao.SeckillUserDao;
import com.qsl.seckill.domain.SeckillUser;
import com.qsl.seckill.redis.RedisService;
import com.qsl.seckill.redis.SeckillUserKey;
import com.qsl.seckill.service.SeckillUserService;
import com.qsl.seckill.util.MD5Util;
import com.qsl.seckill.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author DanielQSL
 * @date 2018-10-08
 */
@Service
public class SeckillUserServiceImpl implements SeckillUserService {

    @Autowired
    SeckillUserDao seckillUserDao;

    @Autowired
    RedisService redisService;

    @Override
    public ServerResponse login(LoginVo loginVo) {
        if (loginVo == null) {
            return ServerResponse.createByErrorMessage("无信息");
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        //判断手机号是否存在
        SeckillUser user = getById(Long.parseLong(mobile));
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.MOBILE_NOT_EXIST.getCode(), ResponseCode.MOBILE_NOT_EXIST.getDesc());
        }
        //验证密码
        String dbPassword = user.getPassword();
        String saltDB = user.getSalt();
        String calcPass = MD5Util.formPassToDatabasePass(formPass, saltDB);
        if (!calcPass.equals(dbPassword)) {
            return ServerResponse.createByErrorMessage("密码错误");
        }

        return ServerResponse.createBySuccess(user);
    }

    /**
     * 根据用户id获取用户信息
     * （使用对象级缓存）
     * @param id
     * @return
     */
    @Override
    public SeckillUser getById(long id) {
        //取缓存
        SeckillUser user = redisService.get(SeckillUserKey.getById, "" + id, SeckillUser.class);
        if (user != null) {
            return user;
        }
        //取数据库
        user = seckillUserDao.getById(id);
        if (user != null) {
            redisService.set(SeckillUserKey.getById, "" + id, user);
        }
        return user;
    }

    @Override
    public SeckillUser getByToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }

        SeckillUser user = redisService.get(SeckillUserKey.token, token, SeckillUser.class);
        return user;
    }
}
