package com.rise.shop.service.art.impl;

import com.rise.shop.dao.art.UserDao;
import com.rise.shop.domain.art.mysql.User;
import com.rise.shop.persistence.service.EntityServiceImpl;
import com.rise.shop.service.art.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by wangdi on 15-1-18.
 */
@Service
public class UserServiceImpl extends EntityServiceImpl<User> implements UserService {

    @Resource
    UserDao userDao;

    @Override
    public void setEntityDao() {
        this.entityDao = userDao;
    }
}
