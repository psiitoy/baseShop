package com.rise.shop.web.controller;

import com.rise.shop.domain.art.mongo.ArtistWork;
import com.rise.shop.domain.query.ArtistWorkQuery;
import com.rise.shop.service.art.ArtistWorkService;
import com.rise.shop.view.controller.BaseController;
import com.rise.shop.web.controller.domain.ArtistWorkView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by wangdi on 15-1-9.
 */
@Controller
@RequestMapping("/artistWork")
public class ArtistWorkController extends BaseController<ArtistWorkView, ArtistWork, ArtistWorkQuery> {

    @Resource
    private ArtistWorkService artistWorkService;

    @Override
    protected void setBaseService() {
        this.baseService = artistWorkService;
    }

    @Override
    protected void setBaseEntity() {
        this.domainT = new ArtistWork();
        this.domainD = new ArtistWorkView();
    }

    @Override
    protected void setNamespace() {
        this.namespace = "artistWork";
    }

    @Override
    protected void setChineseName() {
        this.chineseName = "艺术作品";
    }

}
