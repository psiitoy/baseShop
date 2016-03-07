package com.rise.shop.view.utils;

import com.rise.shop.common.ano.ViewMeta;
import com.rise.shop.common.ano.info.ViewMetaInfo;
import com.rise.shop.common.utils.ReflectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by wangdi on 15-1-9.
 */
public class FieldDetailTools {
    protected static Logger logger = LoggerFactory.getLogger(FieldDetailTools.class);

    /**
     * for表单
     *
     * @param obj      domain实体
     * @param hasValue 是否设置字段值
     * @return
     */
    public static List<ViewMetaInfo> bean2FieldMetaInfoList(Object obj, boolean hasValue) {
        List<ViewMetaInfo> list = new ArrayList<ViewMetaInfo>();
        try {
            Class<? extends Object> clazz = obj.getClass();
            Field[] fields = ReflectUtils.getAllClassAndSuperClassFields(clazz);
            for (Field field : fields) {
                String fieldName = field.getName();
                String genericType = field.getGenericType().toString();
                //查询用字段不纳入展示字段
                if ("index".equalsIgnoreCase(fieldName) || "pageSize".equalsIgnoreCase(fieldName) || "pageNo".equalsIgnoreCase(fieldName) || "_id".equalsIgnoreCase(fieldName)) {
                    continue;
                }

                ViewMeta meta = field.getAnnotation(ViewMeta.class);
                if (meta != null) {
                    ViewMetaInfo info = new ViewMetaInfo(fieldName, genericType, meta);
                    if (hasValue) {
                        Object value = getFieldValue(obj, clazz, field);
                        if (value != null && !value.equals("")) {
                            info.setValue(value);
                        }
                    }
                    info.setValidator(genericTypeToValidator(genericType, meta.notnull()));
                    list.add(info);
                }
            }
            Collections.sort(list);
        } catch (SecurityException e) {
            logger.error("bean2FieldMetaInfoList error", e);
        }
        return list;
    }

    private static String genericTypeToValidator(String genericType, boolean notnull) {
        String validator = "";
        if (genericType == null || genericType.equals("")) {
            validator = "";
        } else if ("class java.lang.String".equals(genericType)) {
            validator = "";
        } else if ("class java.lang.Long".equals(genericType)) {
            validator = "digits";
        } else if ("class java.lang.Integer".equals(genericType)) {
            validator = "digits";
        } else if ("class java.lang.Double".equals(genericType)) {
            validator = "number";
        } else if ("java.util.List<java.lang.String>".equals(genericType)) {
            validator = "";
        } else if ("java.util.List<java.lang.Long>".equals(genericType)) {
            validator = "digits";
        } else if ("java.util.List<java.lang.Integer>".equals(genericType)) {
            validator = "digits";
        } else if ("java.util.List<java.lang.Double>".equals(genericType)) {
            validator = "number";
        } else {
            validator = "";
        }
        if (notnull) {
            validator += " required";
        }
        return validator;
    }

    /**
     * 获取字段值
     *
     * @param obj
     * @param clazz
     * @param field
     * @return
     */
    private static Object getFieldValue(Object obj, Class clazz, Field field) {
        Object resultObj = null;
        ViewMeta meta = field.getAnnotation(ViewMeta.class);
        if (meta.save()) {
            String fieldName = field.getName();
            String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length());
            Method method = null;
            try {
                method = clazz.getMethod(methodName);
            } catch (Exception e) {
                logger.error("getFieldValue error", e);
                return null;
            }
            try {
                resultObj = method.invoke(obj);
            } catch (Exception e) {
                logger.error("getFieldValue error", e);
                return null;
            }
        } else {
            String reffield = meta.reffield();
            try {
                if ("birth".equals(reffield)) {
                    Field refField = clazz.getDeclaredField(reffield);
                    Long birth = (Long) getFieldValue(obj, clazz, refField);
                    Field deathField = clazz.getDeclaredField("death");
                    Long death = (Long) getFieldValue(obj, clazz, deathField);
                    if (birth != null && death != null) {
                        if (death == 0) {
                            resultObj = new Date().getYear() - new Date(birth).getYear() + "岁";
                        } else {
                            resultObj = "[卒年]" + (new Date(death).getYear() - new Date(birth).getYear()) + "岁";
                        }
                    }
                }
            } catch (NoSuchFieldException e) {
                logger.error("getFieldValue error", e);
            }
        }
        return resultObj;
    }

}
