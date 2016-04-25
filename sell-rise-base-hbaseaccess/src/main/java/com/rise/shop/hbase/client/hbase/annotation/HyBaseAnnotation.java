package com.rise.shop.hbase.client.hbase.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * use to do special resolver annotation
 * 
 * all the annotation in the HyBase should has this Annotation.
 * HyBase use this annotation to filter annotation which need to resolve.
 * 
 * @author asus
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface HyBaseAnnotation {

}
