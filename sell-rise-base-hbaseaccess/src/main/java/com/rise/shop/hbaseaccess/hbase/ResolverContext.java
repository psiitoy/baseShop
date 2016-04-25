package com.rise.shop.hbaseaccess.hbase;

import com.rise.shop.hbaseaccess.hbase.model.FieldSetting;

import java.lang.annotation.ElementType;

public class ResolverContext {
	
	private ElementType elementType;
	
	private FieldSetting resolveFieldSetting;
	
	public void setResolveFieldSetting(FieldSetting resolveFieldSetting) {
		this.resolveFieldSetting = resolveFieldSetting;
	}

	public ElementType getElementType() {
		return elementType;
	}

	public FieldSetting getResolveFieldSetting() {
		return resolveFieldSetting;
	}

	public ResolverContext(ElementType elementType) {
		this.elementType = elementType;
	}
}
