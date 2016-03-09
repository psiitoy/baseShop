package com.rise.shop.service.art.impl;

import com.rise.shop.dao.art.ImageInfoDao;
import com.rise.shop.domain.art.mongo.ImageInfo;
import com.rise.shop.persistence.service.EntityServiceImpl;
import com.rise.shop.service.art.ImageInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by wangdi on 15-1-9.
 */
@Service
public class ImageInfoServiceImpl extends EntityServiceImpl<ImageInfo> implements ImageInfoService {

    @Resource
    private ImageInfoDao imageInfoDao;

    @Override
    public void setEntityDao() {
        this.entityDao = imageInfoDao;
    }

    @Override
    public void setEntityAdapterService() {

    }
}
