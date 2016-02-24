package com.rise.shop.persistence.dao;

import com.rise.shop.persistence.page.PaginatedList;
import com.rise.shop.persistence.query.Query;

import java.util.List;
import java.util.Map;


/**
 * 针对单个Entity对象的操作定义，使用范型的实现
 *
 * @author zhaoyanming
 * @version 2011.12.06
 */
public interface EntityDao<T> {

    /**
     * 依据主键获取记录
     *
     * @param ID
     * @return
     * @throws Exception
     */
    public T get(long ID) throws Exception;

    /**
     * 保存实体
     *
     * @param t
     * @throws org.springframework.dao.DataAccessException
     */
    public T insert(T t) throws Exception;

    /**
     * 更新实体
     *
     * @param t
     * @throws org.springframework.dao.DataAccessException
     */
    public int update(T t) throws Exception;

    /**
     * 删除实体
     *
     * @param t
     * @throws org.springframework.dao.DataAccessException
     */
    public int delete(T t) throws Exception;

    /**
     * 根据指定的条件查询记录
     *
     * @param t
     * @return
     * @throws org.springframework.dao.DataAccessException
     */
    public List<T> findBy(T t) throws Exception;

    /**
     * 根据指定的条件查单个 查出多个则抛异常
     *
     * @param t
     * @throws org.springframework.dao.DataAccessException
     */
    public T findBySingle(T t) throws Exception;

    /**
     * 根据指定的条件分页查询记录
     *
     * @param query
     * @throws org.springframework.dao.DataAccessException
     */
    public PaginatedList<T> findByPage(Query query) throws Exception;

    /**
     * 根据指定的条件分页查询记录
     *
     * @param sqlID sqlmap 中的ID
     * @throws org.springframework.dao.DataAccessException
     */
    public PaginatedList<T> findByPage(String sqlID, Query query) throws Exception;

    /**
     * count (查所有满足条件的个数)
     *
     * @param t
     * @return
     * @throws Exception
     */
    public int count(T t) throws Exception;

    /**
     * 根据指定的条件分页查询记录 模糊查询
     *
     * @param queryMap
     * @throws org.springframework.dao.DataAccessException
     */
    public PaginatedList<T> findByPageLike(Map<String, String> queryMap) throws Exception;

}
