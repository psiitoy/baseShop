package com.rise.shop.web.controller;

import com.rise.shop.domain.art.mongo.Artist;
import com.rise.shop.domain.query.ArtistQuery;
import com.rise.shop.service.art.ArtistService;
import com.rise.shop.view.controller.BaseController;
import com.rise.shop.web.controller.domain.ArtistView;
import com.rise.shop.web.validator.ArtistValidator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by wangdi on 15-1-9.
 */
@Controller
@RequestMapping("/artist")
public class ArtistController extends BaseController<ArtistView, Artist, ArtistQuery> {

    @Resource
    private ArtistService artistService;

    @Override
    protected void setBaseService() {
        this.baseService = artistService;
    }

    @Override
    protected void setBaseEntity() {
        this.domainPersistence = new Artist();
        this.domainView = new ArtistView();
    }

    @Override
    protected void setNamespace() {
        this.namespace = "artist";
    }

    @Override
    protected void setChineseName() {
        this.chineseName = "艺术家";
    }

    @Override
    protected void setValidator() {
        this.validator = new ArtistValidator();
    }
}
