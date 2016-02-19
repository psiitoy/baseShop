package com.rise.shop.persistence.generate;

import com.rise.shop.persistence.beans.BasePersistenceBean;
import com.rise.shop.persistence.dao.EntityDao;
import com.rise.shop.persistence.query.Query;
import com.rise.shop.persistence.utils.ReflectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by wangdi on 15-7-17.
 */
public abstract class EntityDaoBaseTest<Domain extends BasePersistenceBean> {

    public final static Logger logger = LoggerFactory.getLogger(EntityDaoBaseTest.class);

    private final static int totalPage = 10;
    private final static int pageSize = 10;

    private final static int maxPageSize = 1000;

    public abstract EntityDao<Domain> getEntityDao();

    public abstract Class<Domain> getDomain();

    public abstract Query getQuery();

    private List<Long> tempIdList;

    private int count;

    public void execute() throws Exception {
        try {
            tempIdList = new ArrayList<Long>();
            count = testCount(getQuery());
            logger.info("[DB框架测试]testCount 成功");
            testInsert(getDomain(), getQuery());
            logger.info("[DB框架测试]testInsert 成功");
            testQueryPage(getQuery());
            logger.info("[DB框架测试]testQueryPage 成功");
            testGetAndUpdate(getDomain());
            logger.info("[DB框架测试]testGetAndUpdate 成功");
        } catch (Exception e) {
            logger.error("[DB框架测试]execute error", e);
        } finally {
            testDelete(getDomain(), getQuery());
            logger.info("[DB框架测试]testDelete 成功 [finish]");
        }
    }

    public int testCount(Query query) throws Exception {
        return getEntityDao().count(query);
    }

    public void testInsert(Class<Domain> domainClass, Query query) throws Exception {
        for (int i = 0; i < pageSize * pageSize; i++) {
            Domain t = domainClass.newInstance();
            t = ReflectUtils.setFieldNullToRandomValue(t);
            tempIdList.add(t.getId());
            getEntityDao().insert(t);
        }
        int nowcount = (totalPage * pageSize + count);
        Assert.isTrue(getEntityDao().count(query) == nowcount);
        Assert.isTrue(getEntityDao().findBy(domainClass.newInstance()).size() == nowcount);
        query.setPageNo(0);
        query.setPageSize(maxPageSize);
        Assert.isTrue(getEntityDao().findByPage(query).size() == nowcount);
    }

    public void testGetAndUpdate(Class<Domain> domainClass) throws Exception {
        Domain t = domainClass.newInstance();
        t.setId(tempIdList.get(new Random().nextInt(tempIdList.size())));
        Assert.notNull(t);
        Assert.isTrue(getEntityDao().update(t) > 0);
    }

    public void testQueryPage(Query query) throws Exception {
        query.setPageSize(pageSize);
        query.setIndex(null);
        query.setPageNo(0);
        Long tempIdIndex1 = 0l;
        //第一页
        List<Domain> pages = getEntityDao().findByPage(query);
        Assert.isTrue(pages.size() == pageSize);
        tempIdIndex1 = pages.get(1).getId();
        query.setIndex(1);
        query.setPageNo(0);
        //第一页 第二个开始 同第一次查询的第二个id
        pages = getEntityDao().findByPage(query);
        Assert.isTrue(pages.get(0).getId().equals(tempIdIndex1));
        query.setIndex(null);
        query.setPageNo(totalPage - 1);
        //最后一页
        pages = getEntityDao().findByPage(query);
        Assert.isTrue(pages.size() == pageSize);
        query.setIndex(null);
        query.setPageNo(totalPage);
        //最后一页后一页
        pages = getEntityDao().findByPage(query);
        Assert.isTrue(pages.size() == count % pageSize);
    }

    public void testDelete(Class<Domain> domainClass, Query query) throws Exception {
        for (Long id : tempIdList) {
            Domain t = domainClass.newInstance();
            t.setId(id);
            getEntityDao().delete(t);
        }
        query.setPageSize(maxPageSize);
        query.setPageNo(1);
        Assert.isTrue(getEntityDao().count(query) == count);
        Assert.isTrue(getEntityDao().findByPage(query).size() == count);
        Assert.isTrue(getEntityDao().findBy(domainClass.newInstance()).size() == count);
    }

}
