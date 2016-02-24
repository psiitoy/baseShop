package com.rise.shop.persistence.generate;

import com.rise.shop.persistence.beans.BasePersistenceBean;
import com.rise.shop.persistence.query.DefaultBaseQuery;
import com.rise.shop.persistence.query.domain.IntervalSuffixEnum;
import com.rise.shop.persistence.utils.EntityNamesUtils;
import com.rise.shop.persistence.utils.ReflectUtils;
import org.bson.types.ObjectId;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 根据定义的bean（BasePersistenceBean的子类） 自动生成ibatis的xml工具(可以main方法打印)
 * Created by wangdi on 15-7-17.
 */
public class EntityDaoIBatisXmlUtil {

    public static final String attention = " * 根据定义的bean（BasePersistenceBean的子类） 自动生成ibatis的xml工具(可以main方法打印)\n" +
            " * 参数命名规则需要符合 java规范如userName 自动生成的对应db属性命名为 USER_NAME\n" +
            " * 表名为类的简写名 如 PopUser\n" +
            " * namespace可自行定义 默认为类全路径驼峰格式 如comJdUser\n";

    public static <Domain extends BasePersistenceBean, DomainQuery> String makeXml(Class<Domain> domainClass, Class<DomainQuery> domainQueryClass) {
        StringBuilder sb = new StringBuilder();
        sb.append(getNameSpace(domainClass));
        sb.append(getTypeAlias(domainClass, domainQueryClass));
        sb.append(getResultMap(domainClass));
        sb.append(getSqlQuery(domainClass, domainQueryClass));
        sb.append(getSqlBaseColumnList(domainClass));
        sb.append(getSqlGet(domainClass));
        sb.append(getSqlFind(domainClass));
        sb.append(getSqlCount(domainClass));
        sb.append(getSqlFindByPage(domainClass));
        sb.append(getSqlInsert(domainClass));
        sb.append(getSqlUpdate(domainClass));
        sb.append(getDelete(domainClass));
        sb.append("\n");
        return sb.toString();
    }

    private static <Domain extends BasePersistenceBean> String getNameSpace(Class<Domain> domainClass) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<!DOCTYPE sqlMap PUBLIC \"-//ibatis.apache.org//DTD SQL Map 2.0//EN\" \"http://ibatis.apache.org/dtd/sql-map-2.dtd\">\n" +
                "<sqlMap namespace=\"");
        sb.append(EntityNamesUtils.getHumpClassNames(domainClass.getName()));
        sb.append("\">");
        return sb.toString();
    }

    private static <Domain extends BasePersistenceBean, DomainQuery> String getTypeAlias(Class<Domain> domainClass, Class<DomainQuery> domainQueryClass) {
        StringBuilder sb = new StringBuilder();
        sb.append("<typeAlias alias=\"");
        sb.append(domainClass.getSimpleName());
        sb.append("\" type=\"");
        sb.append(domainClass.getName());
        sb.append("\"/>");
        sb.append("<typeAlias alias=\"");
        sb.append(domainClass.getSimpleName() + "Query");
        sb.append("\" type=\"");
        sb.append(domainQueryClass.getName());
        sb.append("\"/>");
        return sb.toString();
    }

    private static <Domain extends BasePersistenceBean> String getResultMap(Class<Domain> domainClass) {
        StringBuilder sb = new StringBuilder();
        sb.append("<!-- 返回结果集类型 -->");
        sb.append("<resultMap id=\"");
        sb.append(domainClass.getSimpleName() + "ResultMap");
        sb.append("\" class=\"");
        sb.append(domainClass.getSimpleName());
        sb.append("\">");
        sb.append(getResultMapColumn(domainClass));
        sb.append("</resultMap>");
        return sb.toString();
    }

    private static <Domain extends BasePersistenceBean> String getResultMapColumn(Class<Domain> domainClass) {
        StringBuilder sb = new StringBuilder();
        Field[] fields = ReflectUtils.getAllClassAndSuperClassFields(domainClass);
        for (Field field : fields) {
            if (field.getType().isAssignableFrom(ObjectId.class)) {
                continue;
            }
            sb.append("<result column=\"");
            sb.append(EntityNamesUtils.getSQLFieldName(field.getName()));
            sb.append("\" property=\"");
            sb.append(field.getName());
            sb.append("\"/>");
        }
        return sb.toString();
    }

    private static <Domain extends BasePersistenceBean, DomainQuery> String getSqlQuery(Class<Domain> domainClass, Class<DomainQuery> domainQueryClass) {
        StringBuilder sb = new StringBuilder();
        sb.append("<!--[domain所有条件] -->");
        sb.append("<sql id=\"allCondition\">");
        sb.append("<dynamic prepend=\"WHERE\">");
        sb.append(getDynamicWhereCloumn(domainClass));
        sb.append("</dynamic>");
        sb.append("</sql>");
        sb.append("<!--[查询条件] -->");
        sb.append("<sql id=\"queryCondition\">");
        sb.append("<dynamic prepend=\"WHERE\">");
        sb.append(getDynamicWhereCloumn(domainQueryClass));
        sb.append("</dynamic>");
        sb.append("</sql>");
        return sb.toString();
    }

    private static <DomainQuery> String getDynamicWhereCloumn(Class<DomainQuery> domainQueryClass) {
        StringBuilder sb = new StringBuilder();
        Field[] fields = ReflectUtils.getAllClassAndSuperClassFields(domainQueryClass);
        List<Field> fieldList = Arrays.asList(fields);
        Collections.sort(fieldList, new Comparator<Field>() {
            @Override
            public int compare(Field o1, Field o2) {
                String[] names = new String[]{o1.getName(), o2.getName()};
                Arrays.sort(names);
                return o1.getName().equals(names[0]) ? -1 : 1;
            }
        });
        Field[] pageFields = ReflectUtils.getAllClassAndSuperClassFields(DefaultBaseQuery.class);
        for (Field field : fieldList) {
            if (field.getType().isAssignableFrom(ObjectId.class)) {
                continue;
            }
            /**
             * 除去区间查询字段
             */
            IntervalSuffixEnum intervalSuffixEnum = IntervalSuffixEnum.getIntervalSuffixEnumByFieldName(field.getName());
            if (intervalSuffixEnum != null) {
                sb.append("<isNotEmpty  prepend=\" AND \" property=\"");
                sb.append(field.getName());
                sb.append("\">");
                sb.append("<![CDATA[");
                sb.append(" " + EntityNamesUtils.getSQLFieldName(IntervalSuffixEnum.getRealFieldNameWithoutSuffix(field.getName())) + " " + intervalSuffixEnum.getSymbol() + " #" + field.getName() + "#");
                sb.append("]]>");
                sb.append("</isNotEmpty>");
                continue;
            }
            /**
             * 除去分页查询字段
             */
            boolean isPageFields = false;
            for (Field pf : pageFields) {
                if (pf.getName().equals(field.getName())) {
                    isPageFields = true;
                    break;
                }
            }
            if (isPageFields) {
                continue;
            }
            sb.append("<isNotEmpty prepend=\" AND \" property=\"");
            sb.append(field.getName());
            sb.append("\">");
            sb.append(" " + EntityNamesUtils.getSQLFieldName(field.getName()) + "=#" + field.getName() + "#");
            sb.append("</isNotEmpty>");
        }
        return sb.toString();
    }

    private static <Domain extends BasePersistenceBean> String getSqlBaseColumnList(Class<Domain> domainClass) {
        StringBuilder sb = new StringBuilder();
        sb.append("<sql id=\"Base_Column_List\">");
        Field[] fields = ReflectUtils.getAllClassAndSuperClassFields(domainClass);
        for (Field field : fields) {
            if (field.getType().isAssignableFrom(ObjectId.class)) {
                continue;
            }
            sb.append(" " + EntityNamesUtils.getSQLFieldName(field.getName()) + ",");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append("</sql>");
        return sb.toString();
    }

    private static <Domain extends BasePersistenceBean> String getSqlGet(Class<Domain> domainClass) {
        StringBuilder sb = new StringBuilder();
        sb.append("<!--根据主键查单条-->");
        sb.append("<select id=\"Get\" parameterClass=\"long\" resultMap=\"");
        sb.append(domainClass.getSimpleName() + "ResultMap");
        sb.append("\">");
        sb.append(" SELECT");
        sb.append("<include refid=\"Base_Column_List\"/>");
        sb.append(" FROM " + EntityNamesUtils.getSQLTableName(domainClass.getSimpleName()));
        sb.append(" WHERE id=#id:BIGINT#");
        sb.append("</select>");
        return sb.toString();
    }

    private static <Domain extends BasePersistenceBean> String getSqlFind(Class<Domain> domainClass) {
        StringBuilder sb = new StringBuilder();
        sb.append("<!--根据条件查单条-->");
        sb.append("<select id=\"Find\" parameterClass=\"");
        sb.append(domainClass.getSimpleName());
        sb.append("\" resultMap=\"");
        sb.append(domainClass.getSimpleName() + "ResultMap");
        sb.append("\">");
        sb.append(" SELECT");
        sb.append("<include refid=\"Base_Column_List\"/>");
        sb.append(" FROM " + EntityNamesUtils.getSQLTableName(domainClass.getSimpleName()));
        sb.append("<isParameterPresent>");
        sb.append("<include refid=\"allCondition\"/>");
        sb.append("</isParameterPresent>");
        sb.append("</select>");
        return sb.toString();
    }

    private static <Domain extends BasePersistenceBean> String getSqlCount(Class<Domain> domainClass) {
        StringBuilder sb = new StringBuilder();
        sb.append("<!--根据条件查条数-->");
        sb.append("<select id=\"Count\" parameterClass=\"");
        sb.append(domainClass.getSimpleName());
        sb.append("\" resultClass=\"int\">");
        sb.append(" SELECT count(1) FROM " + EntityNamesUtils.getSQLTableName(domainClass.getSimpleName()));
        sb.append("<isParameterPresent>");
        sb.append("<include refid=\"allCondition\"/>");
        sb.append("</isParameterPresent>");
        sb.append("</select>");
        return sb.toString();
    }

    private static <Domain extends BasePersistenceBean> String getSqlFindByPage(Class<Domain> domainClass) {
        StringBuilder sb = new StringBuilder();
        sb.append("<!--根据条件分页查询-->");
        sb.append("<select id=\"FindByPage\" parameterClass=\"");
        sb.append(domainClass.getSimpleName() + "Query");
        sb.append("\" resultMap=\"");
        sb.append(domainClass.getSimpleName() + "ResultMap");
        sb.append("\">");
        sb.append(" SELECT");
        sb.append("<include refid=\"Base_Column_List\"/>");
        sb.append(" FROM " + EntityNamesUtils.getSQLTableName(domainClass.getSimpleName()));
        sb.append("<isParameterPresent>");
        sb.append("<include refid=\"queryCondition\"/>");
        sb.append("</isParameterPresent>");
        //增加 orderBy
        sb.append("<isParameterPresent>");
        sb.append("<isNotEmpty prepend=\"ORDER BY \" property=\"orderBy\"> ");
        sb.append("$orderBy$");
        sb.append("</isNotEmpty></isParameterPresent>");
        sb.append("limit #index# ,#pageSize#");
        sb.append("</select>");
        return sb.toString();
    }

    private static <Domain extends BasePersistenceBean> String getSqlInsert(Class<Domain> domainClass) {
        StringBuilder sb = new StringBuilder();
        sb.append("<!--插入-->");
        sb.append("<insert id=\"Insert\" parameterClass=\"");
        sb.append(domainClass.getSimpleName());
        sb.append("\">");
        sb.append(" INSERT INTO " + EntityNamesUtils.getSQLTableName(domainClass.getSimpleName()));
        sb.append(" (<include refid=\"Base_Column_List\"/>)");
        sb.append(" VALUES (");
        Field[] fields = ReflectUtils.getAllClassAndSuperClassFields(domainClass);
        for (Field field : fields) {
            if (field.getType().isAssignableFrom(ObjectId.class)) {
                continue;
            }
            if ("created".equals(field.getName()) || "modified".equals(field.getName())) {
                sb.append(" now(),");
            } else {
                sb.append(" #" + field.getName() + "#,");
            }
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append(")");
        sb.append("</insert>");
        return sb.toString();
    }

    private static <Domain extends BasePersistenceBean> String getSqlUpdate(Class<Domain> domainClass) {
        StringBuilder sb = new StringBuilder();
        sb.append("<!--更新-->");
        sb.append("<update id=\"Update\" parameterClass=\"");
        sb.append(domainClass.getSimpleName());
        sb.append("\">");
        sb.append(" UPDATE " + EntityNamesUtils.getSQLTableName(domainClass.getSimpleName()));
        sb.append(" SET MODIFIED=now()");
        sb.append(" " + getDynamicWhereUpdateCloumn(domainClass));
        sb.append(" WHERE id=#id:BIGINT#");
        sb.append("</update>");
        return sb.toString();
    }

    private static <Domain extends BasePersistenceBean> String getDynamicWhereUpdateCloumn(Class<Domain> domainClass) {
        StringBuilder sb = new StringBuilder();
        Field[] fields = ReflectUtils.getAllClassAndSuperClassFields(domainClass);
        Field[] basePersistenceFields = ReflectUtils.getAllClassAndSuperClassFields(BasePersistenceBean.class);
        for (Field field : fields) {
            if (field.getType().isAssignableFrom(ObjectId.class)) {
                continue;
            }
            boolean isBase = false;
            for (Field bf : basePersistenceFields) {
                if (bf.getName().equalsIgnoreCase(field.getName())) {
                    isBase = true;
                    break;
                }
            }
            if (isBase) {
                continue;
            }
            sb.append("<isNotEmpty property=\"");
            sb.append(field.getName());
            sb.append("\">");
            sb.append(" ," + EntityNamesUtils.getSQLFieldName(field.getName()) + "=#" + field.getName() + "#");
            sb.append("</isNotEmpty>");
        }
        return sb.toString();
    }

    private static <Domain extends BasePersistenceBean> String getDelete(Class<Domain> domainClass) {
        StringBuilder sb = new StringBuilder();
        sb.append("<!--删除-->");
        sb.append("<delete id=\"Delete\" parameterClass=\"");
        sb.append(domainClass.getSimpleName());
        sb.append("\">");
        sb.append(" DELETE FROM " + EntityNamesUtils.getSQLTableName(domainClass.getSimpleName()));
        sb.append(" WHERE id=#id:BIGINT#");
        sb.append("</delete>");
        sb.append("</sqlMap>");
        return sb.toString();
    }

}