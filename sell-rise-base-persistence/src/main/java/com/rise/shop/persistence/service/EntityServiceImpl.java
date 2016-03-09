package com.rise.shop.persistence.service;

import com.rise.shop.persistence.dao.EntityDao;
import com.rise.shop.persistence.page.PaginatedList;
import com.rise.shop.persistence.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * Created by wangdi on 14-12-10.
 */
public abstract class EntityServiceImpl<T> implements EntityService<T> {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected EntityDao<T> entityAdapterService;
    protected EntityDao<T> entityDao;

    @PostConstruct
    public abstract void setEntityDao();

    @PostConstruct
    public abstract void setEntityAdapterService();

    @Override
    public T get(long ID) throws Exception {
        if (entityAdapterService != null) {
            return entityAdapterService.get(ID);
        } else {
            return entityDao.get(ID);
        }
    }

    @Override
    public T insert(T t) throws Exception {
        if (entityAdapterService != null) {
            return entityAdapterService.insert(t);
        } else {
            return entityDao.insert(t);
        }
    }

    @Override
    public int update(T t) throws Exception {
        if (entityAdapterService != null) {
            return entityAdapterService.update(t);
        } else {
            return entityDao.update(t);
        }
    }

    @Override
    public int delete(T t) throws Exception {
        if (entityAdapterService != null) {
            return entityAdapterService.delete(t);
        } else {
            return entityDao.delete(t);
        }
    }

    @Override
    public List<T> findBy(T t) throws Exception {
        if (entityAdapterService != null) {
            return entityAdapterService.findBy(t);
        } else {
            return entityDao.findBy(t);
        }
    }

    @Override
    public PaginatedList findByPage(Query query) throws Exception {
        if (entityAdapterService != null) {
            return entityAdapterService.findByPage(query);
        } else {
            return entityDao.findByPage(query);
        }
    }

    @Override
    public int count(T t) throws Exception {
        if (entityAdapterService != null) {
            return entityAdapterService.count(t);
        } else {
            return entityDao.count(t);
        }
    }

    @Override
    public PaginatedList findByPageLike(PaginatedList paginatedList, Map<String, String> queryMap) throws Exception {
        if (entityAdapterService != null) {
            return entityAdapterService.findByPageLike(queryMap);
        } else {
            return entityDao.findByPageLike(queryMap);
        }
    }
}
