package com.hjc.common.annotation;

import java.lang.annotation.*;

/**
 * 自定义autowired注解
 * @author han
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface NXAutowired {

    String value() default "";
}
