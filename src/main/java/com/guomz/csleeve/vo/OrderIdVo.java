package com.guomz.csleeve.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 用于返回给前端的订单id
 */
@Getter
@Setter
@NoArgsConstructor
public class OrderIdVo {

    private Long orderId;

    public OrderIdVo(Long orderId){
        this.orderId = orderId;
    }
}
