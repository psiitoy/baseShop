package com.rise.shop.persistence.dao.mongo;

import com.mongodb.DBObject;
import com.rise.shop.persistence.beans.BasePersistenceBean;
import com.rise.shop.persistence.dao.mongo.utils.MongoDBManager;
import com.rise.shop.persistence.dao.mongo.utils.MongoUtils;
import com.rise.shop.persistence.page.PaginatedArrayList;
import com.rise.shop.persistence.page.PaginatedList;
import com.rise.shop.persistence.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by wangdi on 15-1-8.
 */
public class BaseMongoDaoImpl<T extends BasePersistenceBean> implements BaseMongoDao<T> {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected String tablePrefix;
    protected String collectionName;
    protected MongoDBManager mongoDBManager;
    protected Class<T> entityClass;

    public BaseMongoDaoImpl() {
        try {
            entityClass = getSuperClassGenricType(getClass(), 0);
            collectionName = entityClass.getSimpleName();
        } catch (Exception e) {
            logger.error("BaseMongoDaoImpl error", e);
        }
    }

    private String getRealCollectionName() {
        return tablePrefix + "_" + collectionName;
    }

    /*
     * 通过反射,获得定义Class时声明的父类的范型参数的类型.
     *
     * @param clazz clazz The class to introspect @param index the Index of the
     * generic ddeclaration,start from 0. @return the index generic declaration,
     * or <code>Object.class</code> if cannot be determined
     */
    private static Class getSuperClassGenricType(Class clazz, int index) {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[index];
    }

    @Override
    public T get(final long ID) throws Exception {
        List<DBObject> list = mongoDBManager.find(getRealCollectionName(), new HashMap<String, Object>() {{
            put("id", ID);
        }});
        if (list != null && list.size() == 1) {
            return (T) MongoUtils.DB2Bean(list.get(0), entityClass.getName());
        } else {
            return null;
        }
    }

    @Override
    public T insert(T t) throws Exception {
        Date nowtime = Calendar.getInstance().getTime();
        t.setCreated(nowtime);
        t.setModified(nowtime);
        if (t.getId() == null || t.getId() == 0) {
            t.setId(System.currentTimeMillis());
        }
        Map<String, Object> map = MongoUtils.bean2Map(t);
        mongoDBManager.insert(getRealCollectionName(), map);
        return t;
    }

    @Override
    public int update(T t) throws Exception {
//        mongoDBManager.update(collectionName, MongoUtils.bean2Map(t), MongoUtils.bean2QueryId(t));
        t.setModified(Calendar.getInstance().getTime());
        return mongoDBManager.update(getRealCollectionName(), MongoUtils.bean2Map(t));
    }

    @Override
    public int delete(T t) throws Exception {
        mongoDBManager.delete(getRealCollectionName(), MongoUtils.bean2Map(t));
        return 0;
    }

    @Override
    public List<T> findBy(T t) throws Exception {
        List<DBObject> list = mongoDBManager.find(getRealCollectionName(), MongoUtils.bean2Map(t));
        List<T> tlist = new ArrayList<T>();
        for (DBObject o : list) {
            tlist.add((T) MongoUtils.DB2Bean(o, entityClass.getName()));
        }
        return tlist;
    }

    @Override
    public T findBySingle(T t) throws Exception {
        List<T> ts = findBy(t);
        if (ts.size() == 1) {
            return ts.get(0);
        } else {
            throw new RuntimeException("findBySingle 查询到多个值 t=" + t);
        }
    }

    @Override
    public PaginatedList<T> findByPage(Query query) throws Exception {
        if (query.getPageNo() == null || query.getPageSize() == null) {
            throw new RuntimeException("query的pageNo和pageSize必须赋值");
        }
        PaginatedList<T> paginatedList = new PaginatedArrayList<T>();
        int count = count(query);
        paginatedList.setPageSize(query.getPageSize());
        paginatedList.setTotalItem(count);
        if (query.getIndex() == null) {
            if (query.getPageNo() < 1) {
                //page从1开始
                query.setPageNo(1);
            }
            query.setIndex((query.getPageNo() - 1) * query.getPageSize());
        }
        //设置当前页
        paginatedList.setIndex(query.getPageNo());
        List<DBObject> list = mongoDBManager.findByPage(getRealCollectionName(), MongoUtils.bean2Map(query), query.getIndex(), query.getPageSize());
        for (DBObject o : list) {
            paginatedList.add((T) MongoUtils.DB2Bean(o, entityClass.getName()));
        }
        return paginatedList;
    }

    @Override
    public PaginatedList<T> findByPage(String sqlID, Query query) throws Exception {
        return findByPage(query);
    }

    //TODO
    @Override
    public PaginatedList<T> findByPageLike(Map<String, String> queryMap) throws Exception {
        PaginatedList<T> paginatedList = new PaginatedArrayList<T>();
        int count = (int) mongoDBManager.getCount(getRealCollectionName(), MongoUtils.bean2LikeMap(queryMap));
        paginatedList.setTotalItem(count);
        List<DBObject> list = mongoDBManager.findByPage(getRealCollectionName(), MongoUtils.bean2LikeMap(queryMap), 0, 100);
        for (DBObject o : list) {
            paginatedList.add((T) MongoUtils.DB2Bean(o, entityClass.getName()));
        }
        return paginatedList;
    }

    @Override
    public int count(Query query) throws Exception {
        return (int) mongoDBManager.getCount(getRealCollectionName(), MongoUtils.bean2Map(query));
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }


    public void setMongoDBManager(MongoDBManager mongoDBManager) {
        this.mongoDBManager = mongoDBManager;
    }

    //    @Required
    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }
}
