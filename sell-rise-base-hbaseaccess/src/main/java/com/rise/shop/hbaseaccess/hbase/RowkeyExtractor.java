package com.rise.shop.hbaseaccess.hbase;

import com.google.common.base.Preconditions;
import com.rise.shop.hbaseaccess.util.ClassUtils;

import java.lang.reflect.Method;

/**
 * Rowkey Parser
 * 
 * only support {$method}
 * 
 */

public class RowkeyExtractor {
	public Method invokeMethod;

	public static RowkeyExtractor extract(String methodName, Class<?> targetType) {
		RowkeyExtractor extractor = new RowkeyExtractor();
		Method method = ClassUtils.getMethod(targetType, methodName);
		Preconditions.checkNotNull(method, "can not get method for type:"
				+ targetType);
		method.setAccessible(true);
		extractor.invokeMethod = method;
		return extractor;
	}

	public byte[] getRowkey(Object object) {
		try {
			return (byte[]) invokeMethod.invoke(object);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
}
