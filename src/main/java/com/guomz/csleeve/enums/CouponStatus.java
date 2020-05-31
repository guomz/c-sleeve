package com.guomz.csleeve.enums;

import lombok.Getter;

@Getter
public enum CouponStatus {

    AVAILABLE(1, "可使用"),
    USED(2, "以使用"),
    EXPIRED(3, "已过期");

    private Integer value;
    private String description;

    CouponStatus(Integer value, String description){
        this.value = value;
        this.description = description;
    }

    public static CouponStatus getCouponStatus(Integer value){
        for (CouponStatus couponStatus: CouponStatus.values()){
            if(couponStatus.getValue() == value){
                return couponStatus;
            }
        }
        return null;
    }
}
