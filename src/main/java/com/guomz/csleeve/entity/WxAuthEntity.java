package com.guomz.csleeve.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 接收微信校验的返回结果
 */
@Getter
@Setter
public class WxAuthEntity {

    @JsonProperty(required = false)
    private String openid;
    @JsonProperty(required = false)
    private String session_key;
    @JsonProperty(required = false)
    private String unionid;
    private String errcode;
    private String errmsg;
}
