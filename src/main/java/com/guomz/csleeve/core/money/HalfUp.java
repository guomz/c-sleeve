package com.guomz.csleeve.core.money;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 四舍五入方式计算折扣
 */
@Component
public class HalfUp implements IMoneyDiscount{
    @Override
    public BigDecimal discount(BigDecimal totalPrice, BigDecimal rate) {

        //保留两位小数采用四舍五入方式
        BigDecimal finalTotalPrice = totalPrice.multiply(rate).setScale(2, RoundingMode.HALF_UP);

        return finalTotalPrice;
    }
}
