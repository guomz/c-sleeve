package com.guomz.csleeve.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 前端分页展示订单列表使用
 */
@Getter
@Setter
@NoArgsConstructor
public class OrderSimplifyVo {

    private Long id;
    private String orderNo;
    private BigDecimal totalPrice;
    private Integer totalCount;
    private String snapImg;
    private String snapTitle;
    private BigDecimal finalTotalPrice;
    private Integer status;
    private Date expiredTime;
    //过期时间
    private Integer period;
}
