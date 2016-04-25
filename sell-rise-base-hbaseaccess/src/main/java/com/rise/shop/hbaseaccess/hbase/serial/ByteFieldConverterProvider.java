package com.rise.shop.hbaseaccess.hbase.serial;

import com.google.common.collect.Maps;
import com.rise.shop.hbaseaccess.util.ClassUtils;

import java.lang.reflect.Type;
import java.util.Map;

@SuppressWarnings({"unchecked", "rawtypes"})
public class ByteFieldConverterProvider {

    public static final String BASE_CONSTRUCTOR = "BASE_CONSTRUCTOR";
    //
    private static Map<String, Class<? extends ByteFieldConverter>> factory = Maps.newHashMap();

    public static void register(String beanId, Class<? extends ByteFieldConverter> type) {
        factory.put(beanId, type);
    }

    public static ByteFieldConverter<?> newInstance(String beanId, Type type) {
        Class<? extends ByteFieldConverter<?>> implClass = (Class<? extends ByteFieldConverter<?>>) factory.get(beanId);
        try {
            ByteFieldConverter converter = ClassUtils.newInstance(implClass);
            converter.setType(type);
            return converter;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

}
