package com.guomz.csleeve.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
//target注解指出该自定义注解会被打在类或接口上面
@Target(ElementType.TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordEqualValidator.class)
public @interface PasswordEqual {

    //以下三个属性为必须有的
    String message() default "两次密码不一致";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    //下面为自定义的
    boolean required() default false;
}
