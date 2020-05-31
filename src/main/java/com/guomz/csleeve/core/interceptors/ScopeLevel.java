package com.guomz.csleeve.core.interceptors;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 此注解用于标识该方法需要的权限等级
 */
@Documented
//target注解指出该自定义注解会被打在类或接口或方法上面
@Target({ElementType.METHOD})
@Retention(RUNTIME)
public @interface ScopeLevel {

    int level() default 8;
}
