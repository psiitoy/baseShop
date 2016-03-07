package com.rise.shop.web.controller;

import com.rise.shop.domain.art.mongo.Exhibition;
import com.rise.shop.persistence.query.BaseQuery;
import com.rise.shop.service.art.ExhibitionService;
import com.rise.shop.view.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by wangdi on 15-1-9.
 */
@Controller
@RequestMapping("/exhibition")
public class ExhibitionController extends BaseController<Exhibition, BaseQuery> {

    @Resource
    private ExhibitionService exhibitionService;

    @Override
    protected void setBaseService() {
        this.baseService = exhibitionService;
    }

    @Override
    protected void setBaseEntity() {
        this.t = new Exhibition();
    }

    @Override
    protected void setNamespace() {
        this.namespace = "exhibition";
    }

    @Override
    protected void setChineseName() {
        this.chineseName = "展览";
    }

}
