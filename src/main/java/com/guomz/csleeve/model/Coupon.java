package com.guomz.csleeve.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
/**
 * 优惠券实体
 */
public class Coupon extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    //优惠券有效期，在核销是判断，领取时根据活动时间判断
    private Date startTime;
    private Date endTime;
    private String description;
    //满多少钱
    private BigDecimal fullMoney;
    //减多少
    private BigDecimal minus;
    private BigDecimal rate;
    private Integer type;
    private Integer valitiy;
    private Long activityId;
    private String remark;
    //是否全场券
    private Boolean wholeStore;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "couponList")
    private List<Category> categoryList;
}
