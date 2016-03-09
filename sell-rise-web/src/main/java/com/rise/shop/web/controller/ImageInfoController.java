package com.rise.shop.web.controller;

import com.rise.shop.domain.art.mongo.ImageInfo;
import com.rise.shop.persistence.query.Query;
import com.rise.shop.service.art.ImageInfoService;
import com.rise.shop.view.controller.BaseController;
import com.rise.shop.web.controller.domain.ImageInfoView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by wangdi on 15-1-9.
 */
@Controller
@RequestMapping("/imageInfo")
public class ImageInfoController extends BaseController<ImageInfoView, ImageInfo, Query> {

    @Resource
    private ImageInfoService imageInfoService;

    @Override
    protected void setBaseService() {
        this.baseService = imageInfoService;
    }

    @Override
    protected void setBaseEntity() {
        this.domainT = new ImageInfo();
        this.domainD = new ImageInfoView();
    }

    @Override
    protected void setNamespace() {
        this.namespace = "imageInfo";
    }

    @Override
    protected void setChineseName() {
        this.chineseName = "图片";
    }

}
