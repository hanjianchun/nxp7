package com.hjc.common.annotation;

import java.lang.annotation.*;

/**
 * 自定义controller注解
 * @author han
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface NXController {
    String value() default "";
}
