package com.rise.shop.service.art.impl;

import com.rise.shop.dao.art.ArtistDao;
import com.rise.shop.domain.art.mongo.Artist;
import com.rise.shop.persistence.service.EntityServiceImpl;
import com.rise.shop.service.art.ArtistService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by wangdi on 15-1-9.
 */
@Service
public class ArtistServiceImpl extends EntityServiceImpl<Artist> implements ArtistService {

    @Resource
    private ArtistDao artistDao;

    @Override
    public void setEntityDao() {
        this.entityDao = artistDao;
    }

    @Override
    public void setEntityAdapterService() {

    }
}
