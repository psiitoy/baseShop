package com.rise.shop.persistence.dao.mongo;

import com.google.common.base.Preconditions;
import com.google.common.reflect.TypeToken;
import com.mongodb.DBObject;
import com.rise.shop.common.utils.CopyPropertyUtils;
import com.rise.shop.persistence.attribute.BasicAttributeEnum;
import com.rise.shop.persistence.dao.mongo.utils.MongoDBManager;
import com.rise.shop.persistence.dao.mongo.utils.MongoUtils;
import com.rise.shop.persistence.page.PaginatedArrayList;
import com.rise.shop.persistence.page.PaginatedList;
import com.rise.shop.persistence.query.OrderByBaseQuery;
import com.rise.shop.persistence.query.Query;
import com.rise.shop.persistence.query.domain.ColumnOrder;
import com.rise.shop.persistence.utils.BasicAttributesUtils;
import com.rise.shop.persistence.utils.IdWorker;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangdi on 15-1-8.
 */
public class BaseMongoDaoImpl<T> implements BaseMongoDao<T> {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected String tablePrefix;
    protected String collectionName;
    protected MongoDBManager mongoDBManager;
    protected Class<T> entityClass;
    /**
     * snowflake Id生成器 begin
     */
    //数据中心id
    private int datacenterId;
    private IdWorker idWorker;
    //snowflake end
    TypeToken<T> typeTokenDomain = new TypeToken<T>(getClass()) {
    };

    public BaseMongoDaoImpl() {
        try {
            entityClass = getSuperClassGenricType(getClass(), 0);
            collectionName = entityClass.getSimpleName();
            idWorker = new IdWorker(entityClass.hashCode() % 30, 0);
        } catch (Exception e) {
            logger.error("BaseMongoDaoImpl error", e);
        }
    }

    /**
     * 获取集合名(数据库)
     *
     * @return
     */
    private String getRealCollectionName() {
        if (StringUtils.isEmpty(tablePrefix)) {
            return collectionName;
        } else {
            return tablePrefix + "_" + collectionName;
        }
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
            put(BasicAttributeEnum.ID.getName(), ID);
        }});
        if (list != null && list.size() == 1) {
            return (T) MongoUtils.DB2Bean(list.get(0), entityClass.getName());
        } else {
            return null;
        }
    }

    @Override
    public T insert(T t) throws Exception {

        if (BasicAttributesUtils.getBasicId(t) == null) {
            BasicAttributesUtils.setBasicId(t, idWorker.nextId());
        }
        BasicAttributesUtils.setBasicCreated(t);
        BasicAttributesUtils.setBasicModify(t);
        Map<String, Object> map = MongoUtils.bean2Map(t);
        mongoDBManager.insert(getRealCollectionName(), map);
        return t;
    }

    @Override
    public int update(T t) throws Exception {
//        mongoDBManager.update(collectionName, MongoUtils.bean2Map(t), MongoUtils.bean2QueryId(t));
        BasicAttributesUtils.setBasicModify(t);
        Map<String, Object> map = MongoUtils.bean2Map(t);
        return mongoDBManager.update(getRealCollectionName(), map);
    }

    @Override
    public int updateCasByModified(T t) throws Exception {
        return update(t);
    }

    @Override
    public int updateCasByCreated(T t) throws Exception {
        return update(t);
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
        Preconditions.checkNotNull(query, "query必须赋值");
        Preconditions.checkNotNull(query.getPageNo(), "query的pageNo和pageSize必须赋值,pageNo从1起(0同1)");
        Preconditions.checkNotNull(query.getPageSize(), "query的pageNo和pageSize必须赋值,pageNo从1起(0同1)");
        PaginatedList<T> paginatedList = new PaginatedArrayList<T>();
        int count = count(CopyPropertyUtils.copyPropertiesAndInstance(query, entityClass));
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
        List<DBObject> list = mongoDBManager.findByPage(getRealCollectionName(), MongoUtils.bean2Map(query), query.getIndex(), query.getPageSize(), getMongoBaseQueryOrderBy(query), query);
        for (DBObject o : list) {
            paginatedList.add((T) MongoUtils.DB2Bean(o, entityClass.getName()));
        }
        return paginatedList;
    }

    private Map<String, Integer> getMongoBaseQueryOrderBy(Query query) {
        if (query instanceof OrderByBaseQuery) {
            OrderByBaseQuery orderByBaseQuery = (OrderByBaseQuery) query;
            Map<String, Integer> orderMap = new HashMap<String, Integer>();
            for (ColumnOrder columnOrder : orderByBaseQuery.getOrderByList()) {
                for (String oName : columnOrder.getColumnNames()) {
                    orderMap.put(oName, columnOrder.getOrderEnum().getMongoOrder());
                }
            }
            return orderMap;
        } else {
            return null;
        }
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
        List<DBObject> list = mongoDBManager.findByPage(getRealCollectionName(), MongoUtils.bean2LikeMap(queryMap), 0, 100, null, null);
        for (DBObject o : list) {
            paginatedList.add((T) MongoUtils.DB2Bean(o, entityClass.getName()));
        }
        return paginatedList;
    }

    @Override
    public int count(T t) throws Exception {
        return (int) mongoDBManager.getCount(getRealCollectionName(), MongoUtils.bean2Map(t));
    }

    @Override
    public Class getDomainClass() {
        return typeTokenDomain.getRawType();
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }


    public void setMongoDBManager(MongoDBManager mongoDBManager) {
        this.mongoDBManager = mongoDBManager;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public void setDatacenterId(int datacenterId) {
        this.datacenterId = datacenterId;
        idWorker = new IdWorker(entityClass.hashCode() % 30, datacenterId);
    }
}
