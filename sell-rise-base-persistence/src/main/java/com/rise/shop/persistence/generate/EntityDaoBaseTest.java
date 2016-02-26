package com.rise.shop.persistence.generate;

import com.rise.shop.persistence.attribute.BasicAttributeEnum;
import com.rise.shop.persistence.dao.EntityDao;
import com.rise.shop.persistence.page.PaginatedList;
import com.rise.shop.persistence.query.DefaultBaseQuery;
import com.rise.shop.persistence.query.Query;
import com.rise.shop.persistence.query.domain.ColumnOrder;
import com.rise.shop.persistence.query.domain.OrderByDescEnum;
import com.rise.shop.persistence.utils.BasicAttributesUtils;
import com.rise.shop.persistence.utils.ReflectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by wangdi on 15-7-17.
 */
public abstract class EntityDaoBaseTest<Domain> {

    public final static Logger logger = LoggerFactory.getLogger(EntityDaoBaseTest.class);

    private final static int pageSize = 10;
    private final static int insertObjCount = 100;
    private final static int maxPageSize = 10000;

    public abstract EntityDao<Domain> getEntityDao();

    public abstract Class<Domain> getDomain();

    public abstract Query getQuery();

    //测试数据id列表
    private List<Long> tempIdList;
    //数据库中原来的数据
    private int beforeCount;
    private Domain domain;

    @PostConstruct
    public void initBefore() {
        tempIdList = new ArrayList<Long>();
    }

    public void execute() throws Exception {
        try {
            domain = getDomain().newInstance();
            beforeCount = testCount(domain);
            logger.info("[DB框架测试]系统中存在" + beforeCount + "条数据");
            logger.info("[DB框架测试]testCount 成功");
            testInsert(getDomain(), getQuery());
            logger.info("[DB框架测试]testInsert 成功");
            testQueryPageFirstAndLast(getQuery());
            logger.info("[DB框架测试]testQueryPage 成功");
            testQueryPageOrderByDescAndAsc();
            logger.info("[DB框架测试]testQueryPageOrderByDescAndAsc 成功");
            testGetAndUpdate(getDomain());
            logger.info("[DB框架测试]testGetAndUpdate 成功");
        } catch (Exception e) {
            logger.error("[DB框架测试]execute error", e);
        } finally {
            testDelete(getDomain(), getQuery());
            logger.info("[DB框架测试]testDelete 成功 [finish]");
        }
    }

    public int testCount(Domain t) throws Exception {
        return getEntityDao().count(t);
    }

    public void testInsert(Class<Domain> domainClass, Query query) throws Exception {
        int count = testCount(domain);
        for (int i = 0; i < insertObjCount; i++) {
            Domain t = domainClass.newInstance();
            t = ReflectUtils.setFieldNullToRandomValue(t);
            Long id = System.currentTimeMillis();
            BasicAttributesUtils.setBasicId(t, id);
            tempIdList.add(BasicAttributesUtils.getBasicId(t));
            getEntityDao().insert(t);
        }
        logger.info("[DB框架测试]插入" + insertObjCount + "条测试数据");
        int nowCount = (insertObjCount + count);
        logger.info("[DB框架测试]当前数据数量为" + nowCount);
        Assert.isTrue(getEntityDao().count(domain) == nowCount);
        Assert.isTrue(getEntityDao().findBy(domainClass.newInstance()).size() == nowCount);
        query.setPageNo(Query.FIRST_PAGE);
        query.setPageSize(maxPageSize);
        Assert.isTrue(getEntityDao().findByPage(query).size() == nowCount);
    }

    public void testGetAndUpdate(Class<Domain> domainClass) throws Exception {
        Domain queryT = domainClass.newInstance();
        BasicAttributesUtils.setBasicId(queryT, tempIdList.get(new Random().nextInt(tempIdList.size())));
        Domain t = getEntityDao().findBySingle(queryT);
        Assert.notNull(t);
        logger.info("[DB框架测试]更新前[id=" + BasicAttributesUtils.getBasicId(queryT) + ",created=" + BasicAttributesUtils.getCreated(queryT) + ",modify=" + BasicAttributesUtils.getModify(queryT) + "]");
        Assert.isTrue(getEntityDao().update(t) > 0);
        t = getEntityDao().findBySingle(queryT);
        logger.info("[DB框架测试]更新后[id=" + BasicAttributesUtils.getBasicId(queryT) + ",created=" + BasicAttributesUtils.getCreated(queryT) + ",modify=" + BasicAttributesUtils.getModify(queryT) + "]");
    }

    public int testQueryTotalPage(Query query) throws Exception {
        int count = testCount(domain);
        query.setPageSize(pageSize);
        query.setIndex(null);
        query.setPageNo(Query.FIRST_PAGE);
        PaginatedList<Domain> pages = getEntityDao().findByPage(query);
        int mathTotalPage = count / pageSize;
        mathTotalPage += count % pageSize > 0 ? 1 : 0;
        Assert.isTrue(mathTotalPage == pages.getTotalPage());
        return pages.getTotalPage();
    }

    public void testQueryPageFirstAndLast(Query query) throws Exception {
        int count = testCount(domain);
        int totalPage = testQueryTotalPage(query);
        //第一页 从index 0开始
        query.setPageSize(pageSize);
        query.setIndex(null);
        query.setPageNo(Query.FIRST_PAGE);
        Long tempIdIndex1 = 0l;
        List<Domain> pages = getEntityDao().findByPage(query);
        Assert.isTrue(pages.size() == pageSize);
        logger.info("[DB框架测试]第" + query.getPageNo() + "页数据量为" + pages.size() + "[PAGESIZE=" + pageSize + ",count=" + count + "]");
        tempIdIndex1 = BasicAttributesUtils.getBasicId(pages.get(1));
        //第一页 从index 1开始 同第一次查询的第二个id
        query.setIndex(1);
        query.setPageNo(Query.FIRST_PAGE);
        pages = getEntityDao().findByPage(query);
        Assert.isTrue(BasicAttributesUtils.getBasicId(pages.get(0)).equals(tempIdIndex1));
        //最后一页
        query.setIndex(null);
        query.setPageNo(totalPage);
        pages = getEntityDao().findByPage(query);
        int lastPageSize = count % pageSize == 0 ? pageSize : count % pageSize;
        Assert.isTrue(pages.size() == lastPageSize);
        logger.info("[DB框架测试]第" + query.getPageNo() + "页数据量为" + pages.size() + "[PAGESIZE=" + pageSize + ",count=" + count + "]");
        //最后一页后一页
        query.setIndex(null);
        query.setPageNo(totalPage + 1);
        pages = getEntityDao().findByPage(query);
        Assert.isTrue(pages.size() == 0);
        logger.info("[DB框架测试]第" + query.getPageNo() + "页数据量为" + pages.size() + "[PAGESIZE=" + pageSize + ",count=" + count + "]");
    }

    public void testQueryPageOrderByDescAndAsc() throws Exception {
        Query query = getQuery();
        if (query instanceof DefaultBaseQuery) {
            DefaultBaseQuery defaultBaseQuery = (DefaultBaseQuery) query;
            defaultBaseQuery.setPageSize(maxPageSize);
            defaultBaseQuery.setIndex(null);
            defaultBaseQuery.setPageNo(Query.FIRST_PAGE);
            defaultBaseQuery.addOrderBy(new ColumnOrder(OrderByDescEnum.DESC, BasicAttributeEnum.ID.getName()));
            List<Domain> pages = getEntityDao().findByPage(defaultBaseQuery);
            Assert.notEmpty(pages);
            Long idBuf = BasicAttributesUtils.getBasicId(pages.get(0));
            defaultBaseQuery.clearOrderBy();
            defaultBaseQuery.addOrderBy(new ColumnOrder(OrderByDescEnum.ASC, BasicAttributeEnum.ID.getName()));
            pages = getEntityDao().findByPage(defaultBaseQuery);
            Assert.notEmpty(pages);
            Assert.isTrue(idBuf.equals(BasicAttributesUtils.getBasicId(pages.get(pages.size() - 1))));
        }
    }

    public void testDelete(Class<Domain> domainClass, Query query) throws Exception {
        for (Long id : tempIdList) {
            Domain t = domainClass.newInstance();
            BasicAttributesUtils.setBasicId(t, id);
            getEntityDao().delete(t);
        }
        logger.info("[DB框架测试]删除" + tempIdList.size() + "条数据");
        query.setPageSize(maxPageSize);
        query.setPageNo(Query.FIRST_PAGE);
        Assert.isTrue(getEntityDao().count(domain) == beforeCount);
        Assert.isTrue(getEntityDao().findByPage(query).size() == beforeCount);
        Assert.isTrue(getEntityDao().findBy(domainClass.newInstance()).size() == beforeCount);
        logger.info("[DB框架测试]删除后库中数据量为" + beforeCount);
    }

    public String generateSqlAndXml() {
        return GenerateSqlAndIbatisXmlTool.generate(getDomain(), getQuery().getClass());
    }

    public String generateSqlAndXml(String tablePrefix) {
        return GenerateSqlAndIbatisXmlTool.generate(getDomain(), getQuery().getClass(), tablePrefix);
    }

}
