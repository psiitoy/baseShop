package com.rise.shop.web.controller;

import com.rise.shop.domain.art.mongo.ImageInfo;
import com.rise.shop.persistence.query.Query;
import com.rise.shop.service.art.ImageInfoService;
import com.rise.shop.web.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by wangdi on 15-1-9.
 */
@Controller
@RequestMapping("/imageInfo")
public class ImageInfoController extends BaseController<ImageInfo, Query> {

    @Resource
    private ImageInfoService imageInfoService;

    @Override
    protected void setBaseService() {
        this.baseService = imageInfoService;
    }

    @Override
    protected void setBaseEntity() {
        this.t = new ImageInfo();
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
