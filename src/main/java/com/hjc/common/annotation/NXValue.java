package com.hjc.common.annotation;


import java.lang.annotation.*;

/**
 * 自定义value注解
 * @author han
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface NXValue {
    String value() default "";
}
