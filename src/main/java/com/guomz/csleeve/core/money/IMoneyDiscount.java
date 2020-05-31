package com.guomz.csleeve.core.money;

import java.math.BigDecimal;
public interface IMoneyDiscount {

    BigDecimal discount(BigDecimal totalPrice, BigDecimal rate);

}
