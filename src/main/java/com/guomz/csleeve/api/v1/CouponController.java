package com.guomz.csleeve.api.v1;

import com.guomz.csleeve.core.LocalUser;
import com.guomz.csleeve.core.UnifyResponse;
import com.guomz.csleeve.enums.CouponStatus;
import com.guomz.csleeve.exception.http.ParameterException;
import com.guomz.csleeve.model.Coupon;
import com.guomz.csleeve.service.CouponServiceImpl;
import com.guomz.csleeve.vo.CouponCategoryPureVo;
import com.guomz.csleeve.vo.CouponPureVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController("/v1/coupon")
public class CouponController {

    @Autowired
    private CouponServiceImpl couponService;

    /**
     * 根据分类获取可领取的优惠券
     * @param categoryId
     * @return
     */
    @GetMapping("/by/category/{categoryId}")
    public List<CouponPureVo> getCouponByCategoryId(@PathVariable("categoryId") Long categoryId){
        List<Coupon> couponList = couponService.getCouponsByCategoryId(categoryId);
        if(couponList == null){
            return Collections.emptyList();
        }
        return CouponPureVo.getList(couponList);
    }

    /**
     * 获取全场券
     * @return
     */
    @GetMapping("/whole_store")
    public List<CouponPureVo> getWholeStoreCoupons(){
        List<Coupon> couponList = couponService.getWholeStoreCoupons();
        if(couponList == null){
            return Collections.emptyList();
        }
        return CouponPureVo.getList(couponList);
    }

    /**
     * 用户领取优惠券
     * @param couponId
     */
    @PostMapping("/collect/{couponId}")
    public void collectCoupon(@PathVariable("couponId") Long couponId){
        Long userId = LocalUser.getUser().getId();
        couponService.collectCoupon(userId, couponId);
        UnifyResponse.crreateSuccess();
    }

    /**
     * 按照优惠券状态找出当前用户的优惠券
     * @param status
     * @return
     */
    @GetMapping("/myself/by/status/{status}")
    public List<CouponPureVo> getCouponByStatus(@PathVariable("status") Integer status){
        Long userId = LocalUser.getUser().getId();
        List<Coupon> couponList = Collections.emptyList();
        switch (CouponStatus.getCouponStatus(status)){
            case AVAILABLE:
                couponList = couponService.getActivedCoupon(userId);
                break;
            case USED:
                couponList = couponService.getUsedCoupon(userId);
                break;
            case EXPIRED:
                couponList = couponService.getExpiredCoupon(userId);
                break;
            default:
                throw new ParameterException(40006);
        }
        return CouponPureVo.getList(couponList);
    }

    /**
     * 返回可使用的优惠券并携带分类信息
     * @return
     */
    @GetMapping("/myself/available/with_category")
    public List<CouponCategoryPureVo> getCouponWithCategory(){
        Long userId = LocalUser.getUser().getId();
        List<Coupon> couponList = couponService.getActivedCoupon(userId);
        return CouponCategoryPureVo.getListWithCategory(couponList);
    }
}
