package com.rise.shop.hbase.client.hbase.serial;

import java.lang.reflect.Type;

/**
 * 
 * Field Converter, F -> T, T -> F
 * 
 */
public interface FieldConverter<F, T> {

	public T convert(F f);
	
	public F reverse(T t);
	
	public void setType(Type type);
}
