package com.rise.shop.service.art.impl;

import com.rise.shop.dao.art.ArtistWorkDao;
import com.rise.shop.domain.art.mongo.ArtistWork;
import com.rise.shop.persistence.service.EntityServiceImpl;
import com.rise.shop.service.art.ArtistWorkService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by wangdi on 15-1-9.
 */
@Service
public class ArtistServiceWorkImpl extends EntityServiceImpl<ArtistWork> implements ArtistWorkService {

    @Resource
    private ArtistWorkDao artistWorkDao;

    @Override
    public void setEntityDao() {
        this.entityDao = artistWorkDao;
    }

    @Override
    public void setEntityAdapterService() {

    }
}
