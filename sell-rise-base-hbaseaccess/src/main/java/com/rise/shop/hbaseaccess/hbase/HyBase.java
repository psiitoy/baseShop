package com.rise.shop.hbaseaccess.hbase;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.rise.shop.hbaseaccess.hbase.annotation.Column;
import com.rise.shop.hbaseaccess.hbase.annotation.Family;
import com.rise.shop.hbaseaccess.hbase.annotation.HyBaseAnnotation;
import com.rise.shop.hbaseaccess.hbase.annotation.Table;
import com.rise.shop.hbaseaccess.hbase.annotation.resolver.ColumnAnnotationResolver;
import com.rise.shop.hbaseaccess.hbase.annotation.resolver.FamilyAnnotationResolver;
import com.rise.shop.hbaseaccess.hbase.annotation.resolver.TableAnnotationResolver;
import com.rise.shop.hbaseaccess.hbase.model.FieldSetting;
import com.rise.shop.hbaseaccess.hbase.model.ObjectMeta;
import com.rise.shop.hbaseaccess.hbase.serial.ByteFieldConverter;
import com.rise.shop.hbaseaccess.hbase.serial.ByteFieldConverterProvider;
import com.rise.shop.hbaseaccess.hbase.serial.impl.JsonByteFieldConverter;
import com.rise.shop.hbaseaccess.hbase.serial.impl.KryoByteFieldConverter;
import com.rise.shop.hbaseaccess.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * open for user interface.
 * parse Model
 * register Model with FieldConverter
 * getHyBaseOpsTemplate instance etc function.
 *
 * @author asus
 *         TODO: add version check, this component should only support Hbase 0.98+
 */
public class HyBase {

	public static Map<Class<?>, ObjectMeta> metaCache = Maps.newHashMap();

	public final static Object lockObject = new Object();

	private static Map<Class<? extends Annotation>, AnnotationResolver> resolvers = Maps.newHashMap();

	static {
		addResolver(Column.class, new ColumnAnnotationResolver());
		addResolver(Family.class, new FamilyAnnotationResolver());
		addResolver(Table.class, new TableAnnotationResolver());
		//register FieldConverter
		ByteFieldConverterProvider.register("kryo", KryoByteFieldConverter.class);
		ByteFieldConverterProvider.register("json", JsonByteFieldConverter.class);
	}

	public static void addResolver(Class<? extends Annotation> key, AnnotationResolver resolver) {
		resolvers.put(key, resolver);
	}

	@SuppressWarnings("rawtypes")
	public static void addSerial(String key, Class<? extends ByteFieldConverter> valueClass) {
		ByteFieldConverterProvider.register(key, valueClass);
	}

	private static boolean isHyBaseAnnotation(Annotation annotation) {
		return annotation.annotationType().getAnnotation(HyBaseAnnotation.class) != null;
	}

	private static AnnotationResolver parseAnnotationResolver(Annotation annotation) {
		if (!isHyBaseAnnotation(annotation)) {
			return null;
		}
		AnnotationResolver resolver = resolvers.get(annotation.annotationType());
		if (resolver == null) {
			for (Annotation childAnnotation : annotation.annotationType().getAnnotations()) {
				resolver = parseAnnotationResolver(childAnnotation);
				if (resolver != null) {
					return resolver;
				}
			}
			throw new IllegalArgumentException("cannot find resolver for annotation:" + annotation.getClass().getName());
		}
		return resolver;
	}

	public static ObjectMeta resolve(Class<?> type) {
		ObjectMeta meta = new ObjectMeta(type);
		Annotation[] classAnnotations = type.getAnnotations();

		for (Annotation classAnnotation : classAnnotations) {
			AnnotationResolver resolver = parseAnnotationResolver(classAnnotation);
			if (resolver != null) {
				ResolverContext rCtx = new ResolverContext(ElementType.TYPE);
				Preconditions.checkNotNull(resolver, "cannot find resolver for annotation:" + classAnnotation);
				resolver.resolve(meta, rCtx);
			}
		}

		Field[] allFields = ClassUtils.getNoStaticFields(type);
		for (Field field : allFields) {
			if (meta.contains(field.getName())) {
				continue;
			}
			field.setAccessible(true);

			Annotation[] fieldAnnotations = field.getAnnotations();
			if (fieldAnnotations == null || fieldAnnotations.length == 0) {
				//TODO whether should add this field to exclude field.
				continue;
			}

			List<Annotation> hyBaseAnnotations = Lists.newArrayList();
			for (Annotation fieldAnnotation : fieldAnnotations) {
				if (isHyBaseAnnotation(fieldAnnotation)) {
					hyBaseAnnotations.add(fieldAnnotation);
				}
			}
			if (hyBaseAnnotations.isEmpty()) {
				//TODO whether should add this field to exclude field.
				continue;
			}
			FieldSetting fieldSetting = new FieldSetting();
			fieldSetting.field = field;

			for (Annotation fieldAnnotation : hyBaseAnnotations) {
				ResolverContext rCtx = new ResolverContext(ElementType.TYPE);
				rCtx.setResolveFieldSetting(fieldSetting);
				AnnotationResolver resolver = parseAnnotationResolver(fieldAnnotation);
				Preconditions.checkNotNull(resolver, "cannot find resolver for annotation:" + fieldAnnotation);
				resolver.resolve(meta, rCtx);
			}
			meta.addFieldSetting(field.getName(), fieldSetting);
		}
		return meta;
	}


	public static ObjectMeta createIfAbsent(Class<?> type) {
		ObjectMeta objectMeta = metaCache.get(type);
		if (objectMeta == null) {
			objectMeta = resolve(type);
			synchronized (lockObject) {
				if (!metaCache.containsKey(type)) {
					metaCache.put(type, objectMeta);
					return objectMeta;
				} else {
					return metaCache.get(type);
				}
			}
		} else {
			return metaCache.get(type);
		}
	}

	public static void loadModelType(Class<?> type) {
		metaCache.put(type, resolve(type));
	}

}
