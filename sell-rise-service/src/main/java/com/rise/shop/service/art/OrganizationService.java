package com.rise.shop.service.art;

import com.rise.shop.domain.art.mongo.Organization;
import com.rise.shop.persistence.service.EntityService;

/**
 * Created by wangdi on 15-1-9.
 */
public interface OrganizationService extends EntityService<Organization> {

    /**
     * 缓存
     *
     * @param id
     * @return
     * @throws Exception
     */
    public Organization queryCacheOrganizationById(long id) throws Exception;
}
