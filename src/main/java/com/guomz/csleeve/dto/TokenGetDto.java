package com.guomz.csleeve.dto;

import com.guomz.csleeve.enums.LoginType;
import com.guomz.csleeve.validators.TokenPassword;
import lombok.Getter;
import lombok.Setter;

/**
 * 接收前端传入的登录信息
 */
@Getter
@Setter
public class TokenGetDto {

    private String account;
    @TokenPassword
    private String password;
    private LoginType type;
}
