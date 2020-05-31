package com.guomz.csleeve.vo;

import com.guomz.csleeve.model.Coupon;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class CouponPureVo {
    private Long id;
    private String title;
    private Date startTime;
    private Date endTime;
    private String description;
    private BigDecimal fullMoney;
    private BigDecimal minus;
    private BigDecimal rate;
    private Integer type;
    private Integer valitiy;
    private Long activityId;
    private String remark;
    private Boolean wholeStore;

    public CouponPureVo(Coupon coupon){
        BeanUtils.copyProperties(coupon, this);
    }

    public static List<CouponPureVo> getList(List<Coupon> couponList){
        List<CouponPureVo> couponPureVoList = couponList.stream().map(coupon -> {
            CouponPureVo couponPureVo = new CouponPureVo(coupon);
            return couponPureVo;
        }).collect(Collectors.toList());
        return couponPureVoList;
    }
}
