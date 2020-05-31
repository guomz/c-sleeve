package com.guomz.csleeve.validators;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 配合tokenpassword注解校验密码
 */
public class TokenPasswordValidator implements ConstraintValidator<TokenPassword, String> {

    private int min;
    private int max;

    @Override
    public void initialize(TokenPassword constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if(StringUtils.isEmpty(value)){
            return true;
        }

        if(value.length() < min || value.length() > max){
            return false;
        }
        return true;
    }
}
