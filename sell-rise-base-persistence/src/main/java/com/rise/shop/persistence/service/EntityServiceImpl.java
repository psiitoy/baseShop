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
    protected EntityDao<T> entityDao;

    @PostConstruct
    public abstract void setEntityDao();

    @PostConstruct
    public abstract void setEntityAdapterService();

    @Override
    public T get(long ID) throws Exception {
        return entityDao.get(ID);
    }

    @Override
    public T insert(T t) throws Exception {
        return entityDao.insert(t);
    }

    @Override
    public int update(T t) throws Exception {
        return entityDao.update(t);
    }

    @Override
    public int delete(T t) throws Exception {
        return entityDao.delete(t);
    }

    @Override
    public List<T> findBy(T t) throws Exception {
        return entityDao.findBy(t);
    }

    @Override
    public PaginatedList findByPage(Query query) throws Exception {
        return entityDao.findByPage(query);
    }

    @Override
    public int count(T t) throws Exception {
        return entityDao.count(t);
    }

    @Override
    public PaginatedList findByPageLike(PaginatedList paginatedList, Map<String, String> queryMap) throws Exception {
        return entityDao.findByPageLike(queryMap);
    }
}
