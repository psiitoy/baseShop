package com.rise.shop.hbase.client.hbase.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@HyBaseAnnotation
public @interface Column {
	String value() default "";
	String serial() default "json";
	int priority() default 2;
}
