package com.rise.shop.hbaseaccess.hbase.reflect;

import org.apache.hadoop.hbase.client.Get;

import java.lang.reflect.Field;

public class HyBaseReflectUtil {
	
	private static Field rowFieldInGet;
	
	static {
		try {
			rowFieldInGet = Get.class.getDeclaredField("row");
		} catch (Exception e) {
			throw new IllegalStateException(e);
		} 
	}

	public static Field getRowFieldInGet() {
		return rowFieldInGet;
	}
	
	public static void setRow(Get get, byte[] row) {
		rowFieldInGet.setAccessible(true);
		try {
			rowFieldInGet.set(get, row);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		} 
	}
	
}
