package com.rise.shop.dao.art.impl;

import com.rise.shop.dao.art.ImageInfoDao;
import com.rise.shop.domain.art.mongo.ImageInfo;
import com.rise.shop.persistence.dao.mongo.BaseMongoDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by wangdi on 15-2-27.
 */
@Repository
public class ImageInfoDaoImpl extends BaseMongoDaoImpl<ImageInfo> implements ImageInfoDao {
}
