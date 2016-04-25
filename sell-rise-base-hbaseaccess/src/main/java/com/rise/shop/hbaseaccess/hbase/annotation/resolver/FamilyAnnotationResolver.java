package com.rise.shop.hbaseaccess.hbase.annotation.resolver;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.rise.shop.hbaseaccess.hbase.AnnotationResolver;
import com.rise.shop.hbaseaccess.hbase.ResolverContext;
import com.rise.shop.hbaseaccess.hbase.annotation.Family;
import com.rise.shop.hbaseaccess.hbase.model.FamilySetting;
import com.rise.shop.hbaseaccess.hbase.model.FieldSetting;
import com.rise.shop.hbaseaccess.hbase.model.ObjectMeta;
import com.rise.shop.hbaseaccess.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.reflect.Field;

public class FamilyAnnotationResolver implements AnnotationResolver {

	public void resolve(ObjectMeta objectMeta, ResolverContext rCtx)  {
		Class<?> type = objectMeta.getModelType();
		if(ElementType.TYPE == rCtx.getElementType()) {
			Annotation defaultFal = ClassUtils.getAnnotation(type, Family.class);
			if (defaultFal != null) {
				FamilySetting defaultFamilySetting = new FamilySetting();
				defaultFamilySetting.name = ClassUtils.invokeAnnotationMethod("value", defaultFal);
				defaultFamilySetting.version = ClassUtils.invokeAnnotationMethod("version", defaultFal);
				
				Preconditions.checkArgument(!Strings.isNullOrEmpty(defaultFamilySetting.name));
				objectMeta.setDefaultFamilySetting(defaultFamilySetting);
			}
		} else if (ElementType.FIELD ==  rCtx.getElementType()) {
			
			FieldSetting fieldSetting = rCtx.getResolveFieldSetting();
			Field field = fieldSetting.field;
			
			Annotation family = ClassUtils.getAnnotation(field, Family.class);
			String familyName = ClassUtils.invokeAnnotationMethod("value", family);
			int version = ClassUtils.invokeAnnotationMethod("version", family);
			version = version > 1 ? version : 1;
			Preconditions.checkArgument(!Strings.isNullOrEmpty(familyName), "family name cannot be null");
			FamilySetting rlt = objectMeta.putIfAbsentFamilySetting(familyName, version);
				
			
			fieldSetting.familySetting = rlt;
		} else {
			throw new IllegalArgumentException("@Family only support Type or Field, not support " + rCtx.getElementType());
		}
		
	}

}
