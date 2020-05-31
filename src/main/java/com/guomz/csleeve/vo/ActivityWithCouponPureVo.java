package com.guomz.csleeve.vo;

import com.guomz.csleeve.model.Activity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ActivityWithCouponPureVo extends ActivityPureVo {

    private List<CouponPureVo> couponPureVoList;

    public ActivityWithCouponPureVo(Activity activity){
        super(activity);
        couponPureVoList = activity.getCouponList().stream().map(coupon -> {
            CouponPureVo couponPureVo = new CouponPureVo(coupon);
            return couponPureVo;
        }).collect(Collectors.toList());
    }
}
