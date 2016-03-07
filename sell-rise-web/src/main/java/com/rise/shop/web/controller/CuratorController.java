package com.rise.shop.web.controller;

import com.rise.shop.domain.art.mongo.Curator;
import com.rise.shop.domain.query.CuratorQuery;
import com.rise.shop.service.art.CuratorService;
import com.rise.shop.view.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by wangdi on 15-1-9.
 */
@Controller
@RequestMapping("/curator")
public class CuratorController extends BaseController<Curator, CuratorQuery> {

    @Resource
    private CuratorService curatorService;

    @Override
    protected void setBaseService() {
        this.baseService = curatorService;
    }

    @Override
    protected void setBaseEntity() {
        this.t = new Curator();
    }

    @Override
    protected void setNamespace() {
        this.namespace = "curator";
    }

    @Override
    protected void setChineseName() {
        this.chineseName = "策展人";
    }

}
