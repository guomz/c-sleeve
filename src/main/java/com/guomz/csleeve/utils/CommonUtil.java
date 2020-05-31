package com.guomz.csleeve.utils;

import com.guomz.csleeve.bo.PageBo;

import java.util.Date;

public class CommonUtil {

    /**
     * 计算页号与页数
     * @param start
     * @param count
     * @return
     */
    public static PageBo convertStartToPage(int start, int count){
        int pageNum = start/count;
        return new PageBo(pageNum, count);
    }

    /**
     * 计算优惠券目前领取的时间是否位于所属活动有效期内
     * @param nowTime
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean isInTimeLine(Date nowTime, Date startTime, Date endTime){
        Long now = nowTime.getTime();
        Long start = startTime.getTime();
        Long end = endTime.getTime();
        return now >= start && now <= end;
    }

    /**
     * 判断订单是否过期
     * @param placedTime 下单时间
     * @param period 单位是秒
     * @return
     */
    public static boolean isOutOfDate(Date placedTime, Long period){
        return new Date().getTime() > placedTime.getTime() + period * 1000;
    }
}
