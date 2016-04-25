package com.rise.shop.hbase.client.util;

import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import org.apache.commons.lang3.reflect.TypeUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

public class ClassUtils {
	
	public static <T> T newInstance(Class<T> clazz) {
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException("instance class: " + clazz + " failed", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T invokeAnnotationMethod(String name, Annotation annotation) {
		try {
			return (T) annotation.annotationType().getDeclaredMethod(name, (Class[])null).invoke(annotation, (Object[])null);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	public static Annotation getAnnotation(Class<?> type, Class<? extends Annotation> superType) {
		if (type.isAnnotationPresent(superType)) {
			return type.getAnnotation(superType);
		} else {
			for (Annotation annotation : type.getAnnotations()) {
				if (annotation.annotationType().isAnnotationPresent(superType)) {
					return annotation;
				}
			}
		}
		return null;
	}
	
	public static Annotation getAnnotation(Field field, Class<? extends Annotation> superType) {
		if (field.isAnnotationPresent(superType)) {
			return field.getAnnotation(superType);
		} else {
			for (Annotation annotation : field.getAnnotations()) {
				if (annotation.annotationType().isAnnotationPresent(superType)) {
					return annotation;
				}
			}
		}
		return null;
	}

	public static Field getField(Class<?> type, final String name) {
		return getField(type, name, false);
	}

	public static Field getField(Class<?> type, final String name, final boolean ignoreCase) {

		return getFields(type, new FieldFilter() {
			
			private boolean first = true;
			
			public boolean filter(Field field) {

				String fieldName = field.getName();
				boolean flag;
				if (ignoreCase) {
					flag = fieldName.equalsIgnoreCase(name);
				} else {
					flag = fieldName.equals(name);
				}
				if (first && flag) {
					first = false;
					return true;
				} else {
					return false;
				}
			}
		})[0];
	}
	
	public static Field[] getNoStaticFields(Class<?> type) {
		return getFields(type, new FieldFilter() {
			public boolean filter(Field field) {
				return field.getModifiers() != Modifier.STATIC;
			}
		});
	}
	
	public static Method getMethod(Class<?> type, final String name) {
		return getMethods(type, new MethodFilter() {
			
			private boolean first = true;
			
			public boolean filter(Method method) {
				if (first & method.getName().equals(name)) {
					first = false;
					return true;
				} else {
					return false;
				}
			}
			
		})[0];
	}
	
	public static Method[] getMethods(Class<?> type, MethodFilter filter) {
		Method[] methods = type.getDeclaredMethods();
		
		Class<?> currentType = type;

		List<Method> resultMethods = Lists.newArrayList();
		while (currentType != Object.class) {
			for (Method method : methods) {
				if (filter != null && filter.filter(method)) {
					resultMethods.add(method);
				}
			}
			currentType = currentType.getSuperclass();
		}
		if (resultMethods.isEmpty()) {
			resultMethods.add(null);
		}
		return resultMethods.toArray(new Method[0]);
	}
	
	public static Field[] getFields(Class<?> type, FieldFilter filter) {
		Class<?> currentType = type;

		List<Field> resultFields = Lists.newArrayList();
		while (currentType != Object.class) {
			Field[] fields = currentType.getDeclaredFields();
			for (Field field : fields) {
				if (filter != null && filter.filter(field)) {
					resultFields.add(field);
				}
			}
			currentType = currentType.getSuperclass();
		}
		if (resultFields.isEmpty()) {
			resultFields.add(null);
		}
		return resultFields.toArray(new Field[resultFields.size()]);
	}
	
	public static interface MethodFilter {
		public boolean filter(Method method);
	}
	
	public static interface FieldFilter {
		public boolean filter(Field field);
	}
	
	static class A {
		public String a;
	}

	static interface B<T> {

	}
	static interface C<A,E> extends B<List<E>> {
	}
	static class F<G> implements C<G,String> {
		public G[] g;
	}

	static class H extends F<String> {}

	static class D {

		public C<String,String> c1;

	}

	public static void main(String[] args) {

		Field field = ClassUtils.getField(H.class, "g");
		System.out.println(H.class.getGenericSuperclass());
		Map map = TypeUtils.getTypeArguments(ParameterizedType.class.cast(H.class.getGenericSuperclass()));
		map = TypeUtils.getTypeArguments(H.class, C.class);
		map = TypeUtils.getTypeArguments(ParameterizedType.class.cast(H.class.getGenericSuperclass()));
		TypeToken token = TypeToken.of(H.class);
		TypeToken token2 = token.getSupertype(B.class);

		System.out.println(token.resolveType(field.getGenericType()));
		System.out.println(token2.resolveType(B.class.getTypeParameters()[0]));
		System.out.println(map);
		System.out.println(field.getGenericType());
	}
}
