package com.rise.shop.service.art.impl;

import com.rise.shop.dao.art.CuratorDao;
import com.rise.shop.domain.art.mongo.Curator;
import com.rise.shop.persistence.service.EntityServiceImpl;
import com.rise.shop.service.art.CuratorService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by wangdi on 15-1-9.
 */
@Service
public class CuratorServiceImpl extends EntityServiceImpl<Curator> implements CuratorService {

    @Resource
    private CuratorDao curatorDao;

    @Override
    public void setEntityDao() {
        this.entityDao = curatorDao;
    }

    @Override
    public void setEntityAdapterService() {

    }
}
