package com.rise.shop.web.controller;

import com.rise.shop.domain.art.mongo.Organization;
import com.rise.shop.domain.query.OrganizationQuery;
import com.rise.shop.service.art.OrganizationService;
import com.rise.shop.view.controller.BaseController;
import com.rise.shop.web.controller.domain.OrganizationView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by wangdi on 15-1-15.
 */
@Controller
@RequestMapping("/organization")
public class OrganizationController extends BaseController<OrganizationView, Organization, OrganizationQuery> {

    @Resource
    OrganizationService organizationService;

    @Override
    protected void setBaseService() {
        this.baseService = organizationService;
    }

    @Override
    protected void setBaseEntity() {
        this.domainPersistence = new Organization();
        this.domainView = new OrganizationView();
    }

    @Override
    protected void setNamespace() {
        this.namespace = "organization";
    }

    @Override
    protected void setChineseName() {
        this.chineseName = "组织机构";
    }

}
