package com.rise.shop.hbaseaccess.hbase.annotation.resolver;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.rise.shop.hbaseaccess.hbase.AnnotationResolver;
import com.rise.shop.hbaseaccess.hbase.ResolverContext;
import com.rise.shop.hbaseaccess.hbase.RowkeyExtractor;
import com.rise.shop.hbaseaccess.hbase.annotation.Table;
import com.rise.shop.hbaseaccess.hbase.model.ObjectMeta;
import com.rise.shop.hbaseaccess.util.ClassUtils;

import java.lang.annotation.Annotation;

public class TableAnnotationResolver implements AnnotationResolver {

	public void resolve(ObjectMeta meta, ResolverContext rCtx) {
		Class<?> type = meta.getModelType();
		Annotation table = ClassUtils.getAnnotation(type, Table.class);
		if (table != null) {
			//1. get the table name.
			String tableName = ClassUtils.invokeAnnotationMethod("name", table);
			Preconditions.checkArgument(!Strings.isNullOrEmpty(tableName));
			
			meta.setTableName(tableName);
			//2. get the rowkey extractor
			String rowkeyExpression = ClassUtils.invokeAnnotationMethod("method", table);
			Preconditions.checkArgument(!Strings.isNullOrEmpty(rowkeyExpression));
			
			RowkeyExtractor rowkeyExtractor = RowkeyExtractor.extract(rowkeyExpression, type);
			Preconditions.checkArgument(rowkeyExtractor != null);
			
			meta.setRowkeyExtractor(rowkeyExtractor);
			//3. exclude fields
			String[] excludeFieldStrs = ClassUtils.invokeAnnotationMethod("exclude", table);
			if (excludeFieldStrs != null && excludeFieldStrs.length > 0) {
				for (String excludeFieldStr : excludeFieldStrs) {
					meta.addExcludeFieldName(excludeFieldStr);
				}
			}
		} else {
			throw new IllegalStateException("cannot resolve ObjectMeta for class:" + type + ", reason: @Table should not be null.");
		}
	}

	

}
