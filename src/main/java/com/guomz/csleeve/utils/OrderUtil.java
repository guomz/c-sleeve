package com.guomz.csleeve.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Calendar;

@Component
public class OrderUtil {

    private static String[] yearCodes;

    @Value("${guo.year-codes}")
    public void setYearCodes(String yearCodes) {
        String[] chars = yearCodes.split(",");
        OrderUtil.yearCodes = chars;
    }

    /**
     * 生成随机的订单号
     * @return
     */
    public static String makeOrderNo() {
        StringBuffer joiner = new StringBuffer();
        Calendar calendar = Calendar.getInstance();
        String mills = String.valueOf(calendar.getTimeInMillis());
        String micro = LocalDateTime.now().toString();
        String random = String.valueOf(Math.random()*1000).substring(0,2);
        joiner.append(OrderUtil.yearCodes[calendar.get(Calendar.YEAR) - 2019])
                .append(Integer.toHexString(calendar.get(Calendar.MONTH)+1).toUpperCase())
                .append(calendar.get(Calendar.DAY_OF_MONTH))
                .append(mills.substring(mills.length()-5, mills.length()))
                .append(micro.substring(micro.length()-3, micro.length()))
                .append(random);
        return joiner.toString();

    }
}
