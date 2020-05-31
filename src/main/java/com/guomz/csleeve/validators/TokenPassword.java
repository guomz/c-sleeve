package com.guomz.csleeve.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
//target注解指出该自定义注解会被打在类或接口或方法上面
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = TokenPasswordValidator.class)
public @interface TokenPassword {
    //以下三个属性为必须有的
    String message() default "密码格式不正确";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    //下面为自定义的
    boolean required() default false;

    int min() default 0;
    int max() default 8;
}
