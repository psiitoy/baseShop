package com.rise.shop.persistence.utils;

import com.rise.shop.persistence.attribute.BasicAttributeEnum;

import java.util.Date;

/**
 * Created by wangdi on 16-2-18.
 */
public class BasicAttributesUtils {

    public static Long getBasicId(Object obj) throws Exception {
        return (Long) ReflectUtils.getFieldValue(obj, BasicAttributeEnum.ID.getName());
    }

    public static Date getModify(Object obj) throws Exception {
        return (Date) ReflectUtils.getFieldValue(obj, BasicAttributeEnum.MODIFIED.getName());
    }

    public static Date getCreated(Object obj) throws Exception {
        return (Date) ReflectUtils.getFieldValue(obj, BasicAttributeEnum.CREATED.getName());
    }

    public static void setBasicId(Object obj, Long id) throws Exception {
        ReflectUtils.setFieldValue(obj, BasicAttributeEnum.ID.getName(), id);
    }

    public static void setBasicModify(Object obj) throws Exception {
        ReflectUtils.setFieldValue(obj, BasicAttributeEnum.MODIFIED.getName(), new Date());
    }

    public static void setBasicCreated(Object obj) throws Exception {
        ReflectUtils.setFieldValue(obj, BasicAttributeEnum.CREATED.getName(), new Date());
    }

}
