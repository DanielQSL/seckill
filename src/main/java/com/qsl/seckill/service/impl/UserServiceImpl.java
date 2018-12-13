package com.qsl.seckill.service.impl;

import com.qsl.seckill.dao.UserDao;
import com.qsl.seckill.domain.User;
import com.qsl.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author DanielQSL
 * @date 2018-09-29
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public User getById(int id) {
        return userDao.getById(id);
    }
}
