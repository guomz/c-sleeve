package com.guomz.csleeve.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
//定义校验方法类，其中泛型里面第一个为注解名称，第二个为被校验的参数类型
//此处使用String其实不正确，因为该自定义注解的目标是类，使用String是为了方便演示
public class PasswordEqualValidator implements ConstraintValidator<PasswordEqual, String> {

    private boolean required = false;

    @Override
    public void initialize(PasswordEqual constraintAnnotation) {
        this.required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }
}
