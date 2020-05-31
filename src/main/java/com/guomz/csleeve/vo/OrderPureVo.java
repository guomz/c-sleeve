package com.guomz.csleeve.vo;

import com.guomz.csleeve.model.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
/**
 * 前端订单详情展示
 */
public class OrderPureVo extends Order {

    private Integer period;
    private Date createTime;

    public OrderPureVo(Order order, int period) {
        BeanUtils.copyProperties(order, this);
        this.period = period;
    }
}
