package com.guomz.csleeve.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * 前端传入的订单信息
 */
@Getter
@Setter
@ApiModel("OrderDto")
public class OrderDto {

    @ApiModelProperty(value = "原始总价")
    private BigDecimal totalPrice;
    @ApiModelProperty(value = "最终总价")
    private BigDecimal finalTotalPrice;
    //使用了哪张优惠券
    @ApiModelProperty(value = "优惠券id")
    private Long couponId;
    @ApiModelProperty(value = "包含单品信息")
    private List<SkuInfoDto> skuInfoDtoList;
    @ApiModelProperty(value = "地址信息")
    private OrderAddressDto orderAddressDto;
}
