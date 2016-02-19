package com.rise.shop.persistence.generate;

import com.rise.shop.persistence.beans.BasePersistenceBean;
import com.rise.shop.persistence.utils.EntityNamesUtils;
import com.rise.shop.persistence.utils.ReflectUtils;

import java.lang.reflect.Field;

/**
 * 根据定义的bean（BasePersistenceBean的子类） 自动生成ibatis的xml工具(main方法打印)
 * 参数命名规则需要符合 java规范如userName 自动生成的对应db属性命名为 USER_NAME
 * 表名为类的简写名 如 PopUser
 * <p/>
 * Created by wangdi on 15-7-17.
 */
public class EntityDaoTableSqlUtil {

    public static <Domain extends BasePersistenceBean, DomainQuery> String makeSql(Class<Domain> domainClass) {
        StringBuilder sb = new StringBuilder();
        sb.append(getCreateTableHead(domainClass));
        sb.append(getColumnSql(domainClass));
        sb.append(getPrimaryKey());
        sb.append(getCreateTableEnd(domainClass));
        return sb.toString();
    }

    private static <Domain extends BasePersistenceBean> String getCreateTableHead(Class<Domain> domainClass) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE `");
        sb.append(EntityNamesUtils.getSQLTableName(domainClass.getSimpleName()));
        sb.append("` (\n");
        return sb.toString();
    }

    private static <Domain extends BasePersistenceBean> String getCreateTableEnd(Class<Domain> domainClass) {
        StringBuilder sb = new StringBuilder();
        sb.append(" ) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='所有注释以及字段默认值和大小需自行设置' \n");
        return sb.toString();
    }

    private static <Domain extends BasePersistenceBean> String getColumnSql(Class<Domain> domainClass) {
        StringBuilder sb = new StringBuilder();
        Field[] fields = ReflectUtils.getFieldsByClass(domainClass);
        for (Field field : fields) {
            sb.append("\t\t `");
            sb.append(EntityNamesUtils.getSQLFieldName(field.getName()));
            sb.append("` ");
            sb.append(getDefaultDbTypeByJavaType(field.getType()));
            sb.append("\n");
        }
////        干掉最后的逗号(倒数第一个字符是换行)
//        sb.deleteCharAt(sb.length() - 2);
        return sb.toString();
    }

    private static String getPrimaryKey() {
        return " PRIMARY KEY (`ID`)";
    }

    private static String getDefaultDbTypeByJavaType(Class<?> clz) {
        String dbType = "";
        if (ReflectUtils.isInteger(clz)) {
            dbType = " INTEGER(10) DEFAULT NULL COMMENT '' ,";
        } else if (ReflectUtils.isLong(clz)) {
            dbType = " BIGINT(50) DEFAULT NULL COMMENT '' ,";
        } else if (ReflectUtils.isDouble(clz)) {
            dbType = " DOUBLE DEFAULT NULL COMMENT '' ,";
        } else if (ReflectUtils.isString(clz)) {
            dbType = " VARCHAR(200) DEFAULT NULL COMMENT '' ,";
        } else if (ReflectUtils.isDate(clz)) {
            dbType = " DATETIME DEFAULT NULL COMMENT '' ,";
        } else {
            throw new RuntimeException("建表对象包含未知类型 [" + clz + "]");
        }
        return dbType;
    }

}
