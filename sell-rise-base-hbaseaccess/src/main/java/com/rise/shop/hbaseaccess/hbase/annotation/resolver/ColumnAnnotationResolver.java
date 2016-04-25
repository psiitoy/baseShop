package com.rise.shop.hbaseaccess.hbase.annotation.resolver;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.rise.shop.hbaseaccess.hbase.AnnotationResolver;
import com.rise.shop.hbaseaccess.hbase.ResolverContext;
import com.rise.shop.hbaseaccess.hbase.annotation.Column;
import com.rise.shop.hbaseaccess.hbase.annotation.Family;
import com.rise.shop.hbaseaccess.hbase.model.FieldSetting;
import com.rise.shop.hbaseaccess.hbase.model.ObjectMeta;
import com.rise.shop.hbaseaccess.hbase.serial.ByteFieldConverter;
import com.rise.shop.hbaseaccess.hbase.serial.ByteFieldConverterProvider;
import com.rise.shop.hbaseaccess.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public class ColumnAnnotationResolver implements AnnotationResolver {

	@SuppressWarnings("unchecked")
	public void resolve(ObjectMeta objectMeta, ResolverContext rCtx) {
		FieldSetting fieldSetting = rCtx.getResolveFieldSetting();

		Annotation column = ClassUtils.getAnnotation(fieldSetting.field, Column.class);
		Preconditions.checkNotNull(column,
				"field should have column annotation.");

		String columnName = ClassUtils.invokeAnnotationMethod("value", column);
		columnName = Strings.isNullOrEmpty(columnName) ? fieldSetting.field
				.getName() : columnName;

		fieldSetting.column = columnName;

		//resolve the family
		Annotation family = ClassUtils.getAnnotation(fieldSetting.field, Family.class);
		if (family == null) {
			fieldSetting.familySetting = objectMeta.getDefaultFamilySetting();
		}

		String serial = ClassUtils.invokeAnnotationMethod("serial", column);
		if (!Strings.isNullOrEmpty(serial)) {
			Type type = fieldSetting.field.getGenericType();
			ByteFieldConverter<Object> instance = (ByteFieldConverter<Object>) ByteFieldConverterProvider
					.newInstance(serial, type);
			fieldSetting.setFieldConverter(instance);
		} else {
			throw new IllegalArgumentException(
					"cannot find the fieldconverter for Class:"
							+ objectMeta.getModelType() + ", method:"
							+ fieldSetting.field);
		}
		// TODO do the Multi Annotation Task.
	}
}
