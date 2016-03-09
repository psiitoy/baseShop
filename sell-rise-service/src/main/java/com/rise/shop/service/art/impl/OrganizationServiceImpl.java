package com.rise.shop.service.art.impl;

import com.google.common.cache.*;
import com.rise.shop.dao.art.OrganizationDao;
import com.rise.shop.domain.art.mongo.Organization;
import com.rise.shop.persistence.service.EntityServiceImpl;
import com.rise.shop.service.art.OrganizationService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Created by wangdi on 15-1-9.
 */
@Service
public class OrganizationServiceImpl extends EntityServiceImpl<Organization> implements OrganizationService {

    @Resource
    private OrganizationDao organizationDao;

    /**
     * 缓存
     */
    LoadingCache<Long, Organization> organizationMapCache;

    private final RemovalListener<Long, Organization> KEY_REMOVE_LISTENER = new RemovalListener<Long, Organization>() {
        @Override
        public void onRemoval(RemovalNotification<Long, Organization> removalNotification) {
            if (removalNotification == null) {
                return;
            }
            Long id = removalNotification.getKey();
            logger.info("--KEY_REMOVE_LISTENER-- OrganizationId: {}", id);
        }
    };


    private final CacheLoader<Long, Organization> cacheLoader = new CacheLoader<Long, Organization>() {
        @Override
        public Organization load(Long id) throws Exception {
            Organization ware = get(id);
            logger.info("load to cache OrganizationId: {}  Organization: {}", id, ware);
            return ware;
        }
    };

    /**
     * 初始化缓存
     */
    @PostConstruct
    public void initWareMapCache() {
        organizationMapCache = CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(10, TimeUnit.MINUTES).removalListener(KEY_REMOVE_LISTENER).build(cacheLoader);
    }


    @Override
    public void setEntityDao() {
        this.entityDao = organizationDao;
    }

    @Override
    public void setEntityAdapterService() {

    }

    @Override
    public Organization queryCacheOrganizationById(long id) throws Exception {
        return organizationMapCache.getUnchecked(id);
    }
}
