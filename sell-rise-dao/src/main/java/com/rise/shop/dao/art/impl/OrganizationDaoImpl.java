package com.rise.shop.dao.art.impl;

import com.rise.shop.dao.art.OrganizationDao;
import com.rise.shop.domain.art.mongo.Organization;
import com.rise.shop.persistence.dao.mongo.BaseMongoDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by wangdi on 15-1-8.
 */
@Repository
public class OrganizationDaoImpl extends BaseMongoDaoImpl<Organization> implements OrganizationDao {
}
