package com.guomz.csleeve.service;

import com.guomz.csleeve.bo.SkuOrderBo;
import com.guomz.csleeve.core.money.IMoneyDiscount;
import com.guomz.csleeve.enums.CouponType;
import com.guomz.csleeve.exception.http.ForbiddenException;
import com.guomz.csleeve.exception.http.ParameterException;
import com.guomz.csleeve.model.Coupon;
import com.guomz.csleeve.model.UserCoupon;
import com.guomz.csleeve.utils.CommonUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 由于业务关系此类不使用容器进行托管，用于进行优惠券的校验
 */
public class CouponChecker {

    private Coupon coupon;
    private IMoneyDiscount iMoneyDiscount;

    public CouponChecker(Coupon coupon, IMoneyDiscount iMoneyDiscount){
        this.coupon = coupon;
        this.iMoneyDiscount = iMoneyDiscount;
    }

    /**
     * 判断优惠券是否过期
     * 外部调用
     */
    public void isOk(){
        //先验证时间是否在优惠券有效期内
        Date nowTime = new Date();
        if (!CommonUtil.isInTimeLine(nowTime, coupon.getStartTime(), coupon.getEndTime())){
            throw new ForbiddenException(40004);
        }
    }

    /**
     * 验证价格是否正确，供外部调用
     * @param orderFinalTotalPrice 前端传入的订单总价，即使用过优惠券后的价格
     * @param serverTotalPrice 服务器取出的原总价，不包含优惠券
     */
    public void finalTotalPriceisOk(BigDecimal orderFinalTotalPrice, BigDecimal serverTotalPrice){
        //服务器计算的最终价格
        BigDecimal finalTotalPrice;
        switch (CouponType.getCouponType(coupon.getType())){
            //满减
            case FULL_MINUS:
                finalTotalPrice = serverTotalPrice.subtract(coupon.getMinus());
                break;
            //满减折扣
            case FULL_OFF:
                finalTotalPrice = iMoneyDiscount.discount(serverTotalPrice, coupon.getRate());
                break;
             //无门槛
            case NO_THRESHOLD_MINUS:
                finalTotalPrice = serverTotalPrice.subtract(coupon.getMinus());
                if(finalTotalPrice.compareTo(new BigDecimal("0")) <= 0){
                    //无门槛券不能产生价格小于等于零的订单
                    throw new ParameterException(40008);
                }
                break;
            default:
                throw new ParameterException(40009);
        }
        //价格与前端不相等报错
        if (finalTotalPrice.compareTo(orderFinalTotalPrice) != 0){
            throw new ForbiddenException(50008);
        }

    }

    /**
     * 判断优惠券是否可用，非全场券根据价格判断
     * 外部调用
     * 该方法即判断满减或有条件的优惠券是否满足使用条件
     */
    public void canBeUsed(List<SkuOrderBo> skuOrderBoList, BigDecimal serverTotalPrice){
        BigDecimal orderCategoryTotalPrice;
        //全场券不做品类验证
        if(coupon.getWholeStore()){
            orderCategoryTotalPrice = serverTotalPrice;
        }
        else {
            List<Long> categoryIdList = coupon.getCategoryList()
                    .stream()
                    .map(category -> category.getId())
                    .collect(Collectors.toList());
            orderCategoryTotalPrice = getSumByAllCategory(skuOrderBoList, categoryIdList);
            canCouponBeUsed(orderCategoryTotalPrice);
        }
    }

    /**
     * 判断订单价格是否满足优惠券使用条件即满100减10要判断是否满100，无门槛券不做判断
     * @param orderCategoryTotalPrice
     */
    private void canCouponBeUsed(BigDecimal orderCategoryTotalPrice){
        switch (CouponType.getCouponType(coupon.getType())){
            case FULL_OFF:
            case FULL_MINUS:
                if (orderCategoryTotalPrice.compareTo(coupon.getFullMoney()) < 0){
                    throw new ParameterException(40008);
                }
                break;
            case NO_THRESHOLD_MINUS:
                break;
            default:
                throw new ParameterException(40009);
        }
    }

    /**
     * 计算所有分类的sku价格总和
     * @param skuOrderBoList
     * @param categoryIdList
     * @return
     */
    private BigDecimal getSumByAllCategory(List<SkuOrderBo> skuOrderBoList, List<Long> categoryIdList){
        return categoryIdList.stream().map(categoryId -> getSumByCategory(skuOrderBoList, categoryId))
                .reduce(BigDecimal::add)
                .orElse(new BigDecimal("0"));
    }

    /**
     * 算出某个分类下所有sku的价格
     * @param skuOrderBoList
     * @param categoryId
     * @return
     */
    private BigDecimal getSumByCategory(List<SkuOrderBo> skuOrderBoList, Long categoryId){
        return skuOrderBoList.stream().filter(skuOrderBo -> skuOrderBo.getCategoryId() == categoryId)
                .map(skuOrderBo -> skuOrderBo.getActualPrice())
                .reduce(BigDecimal::add)
                .orElse(new BigDecimal("0"));
    }
}
