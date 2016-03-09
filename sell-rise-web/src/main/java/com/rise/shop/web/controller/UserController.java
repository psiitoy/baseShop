package com.rise.shop.web.controller;

import com.rise.shop.domain.art.mysql.User;
import com.rise.shop.domain.query.UserQuery;
import com.rise.shop.service.art.UserService;
import com.rise.shop.view.controller.BaseController;
import com.rise.shop.web.controller.domain.UserView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * Created by wangdi on 15-1-18.
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController<UserView, User, UserQuery> {
    @Resource
    UserService userService;

    @Override
    protected void setBaseService() {
        this.baseService = userService;
    }

    @Override
    protected void setBaseEntity() {
        this.domainT = new User();
        this.domainD = new UserView();
    }

    @Override
    protected void setNamespace() {
        this.namespace = "user";
    }

    @Override
    protected void setChineseName() {
        this.chineseName = "用户";
    }

    @RequestMapping(value = {"/view", "/"}, method = RequestMethod.GET)
    public String view() {
        return "console/mysql-view";
    }
}
