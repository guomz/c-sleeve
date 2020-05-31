package com.guomz.csleeve.enums;

import lombok.Getter;

@Getter
public enum CouponType {

    FULL_MINUS(1, "满减券"),
    FULL_OFF(2, "满减折扣券"),
    NO_THRESHOLD_MINUS(3, "无门槛券");

    private Integer value;
    private String description;

    CouponType(Integer value, String description){
        this.value = value;
        this.description = description;
    }

    public static CouponType getCouponType(int value){
        for (CouponType couponType: CouponType.values()){
            if(couponType.getValue() == value){
                return couponType;
            }
        }
        return null;
    }
}
