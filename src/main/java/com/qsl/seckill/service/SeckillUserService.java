package com.qsl.seckill.service;

import com.qsl.seckill.common.ServerResponse;
import com.qsl.seckill.domain.SeckillUser;
import com.qsl.seckill.vo.LoginVo;

/**
 * @author DanielQSL
 * @date 2018-10-08
 */
public interface SeckillUserService {

    SeckillUser getById(long id);

    ServerResponse login(LoginVo loginVo);

    SeckillUser getByToken(String token);
}
