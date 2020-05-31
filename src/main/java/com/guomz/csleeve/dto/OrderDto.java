package com.guomz.csleeve.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * 前端传入的订单信息
 */
@Getter
@Setter
public class OrderDto {

    private BigDecimal totalPrice;
    private BigDecimal finalTotalPrice;
    //使用了哪张优惠券
    private Long couponId;
    private List<SkuInfoDto> skuInfoDtoList;
    private OrderAddressDto orderAddressDto;
}
