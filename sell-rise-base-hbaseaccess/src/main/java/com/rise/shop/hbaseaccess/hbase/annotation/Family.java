package com.rise.shop.hbaseaccess.hbase.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@HyBaseAnnotation
public @interface Family {
	String value() default "";
	int version() default 1;
	int priority() default 1;
}
