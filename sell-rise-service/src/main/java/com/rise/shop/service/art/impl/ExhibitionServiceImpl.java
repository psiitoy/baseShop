package com.rise.shop.service.art.impl;

import com.rise.shop.dao.art.ExhibitionDao;
import com.rise.shop.domain.art.mongo.Exhibition;
import com.rise.shop.persistence.service.EntityServiceImpl;
import com.rise.shop.service.art.ExhibitionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by wangdi on 15-1-9.
 */
@Service
public class ExhibitionServiceImpl extends EntityServiceImpl<Exhibition> implements ExhibitionService {

    @Resource
    private ExhibitionDao exhibitionDao;

    @Override
    public void setEntityDao() {
        this.entityDao = exhibitionDao;
    }
}
