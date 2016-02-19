package com.rise.shop.persistence.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

/**
 * Created by wangdi on 15-12-10.
 */
public class CopyPropertyUtils {

    private static final Logger logger = Logger.getLogger(CopyPropertyUtils.class);

    public static void copyProperties(Object dest, Object orig) {
        try {
            PropertyUtils.copyProperties(dest, orig);
        } catch (Exception e) {
            logger.error("copyProperty dest=" + JSON.toJSONString(dest) + ",orig=" + JSON.toJSONString(orig), e);
            throw new RuntimeException("copyProperties error", e);
        }
    }

    public static <T> T copyPropertiesAndInstance(Object orig, Class<T> toClz) {
        try {
            T t = toClz.newInstance();
            copyProperties(t, orig);
            return t;
        } catch (Exception e) {
            logger.error("copyPropertiesAndInstance orig=" + JSON.toJSONString(orig) + ",toClz=" + toClz, e);
            throw new RuntimeException("copyProperties error", e);
        }
    }
}
