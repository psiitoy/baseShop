package com.rise.shop.persistence.generate;

import com.rise.shop.persistence.beans.BasePersistenceBean;

/**
 * Created by wangdi on 16-2-19.
 */
public class GenerateSqlAndIbatisXmlTool {

    public static <Domain extends BasePersistenceBean, DomainQuery> String generate(Class<Domain> domainClass, Class<DomainQuery> domainQueryClass) {
        StringBuilder sb = new StringBuilder();
        sb.append("=========== attention ========================================\n");
        sb.append(EntityDaoTableSqlUtil.attention);
        sb.append("=========== please copy the sql =================================\n");
        sb.append(EntityDaoTableSqlUtil.makeSql(domainClass));
        sb.append("===========end ============================================\n");
        sb.append("=========== attention ========================================\n");
        sb.append(EntityDaoIBatisXmlUtil.attention);
        sb.append("=========== please copy the xml =================================\n");
        sb.append(EntityDaoIBatisXmlUtil.makeXml(domainClass, domainQueryClass));
        sb.append("===========end ============================================\n");
        return sb.toString();
    }

}
