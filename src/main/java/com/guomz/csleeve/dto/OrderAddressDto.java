package com.guomz.csleeve.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//接收前端传入的订单地址相关信息，与微信获取的信息基本一致
public class OrderAddressDto {

    private String userName;
    private String province;
    private String city;
    private String country;
    private String mobile;
    private String nationalCode;
    private String postalCode;
    private String detail;
}
