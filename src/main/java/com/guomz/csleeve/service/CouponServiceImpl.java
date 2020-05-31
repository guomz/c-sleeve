package com.guomz.csleeve.service;

import com.guomz.csleeve.enums.CouponStatus;
import com.guomz.csleeve.exception.http.NotFoundException;
import com.guomz.csleeve.exception.http.ParameterException;
import com.guomz.csleeve.model.Activity;
import com.guomz.csleeve.model.Coupon;
import com.guomz.csleeve.model.UserCoupon;
import com.guomz.csleeve.repository.ActivityRepository;
import com.guomz.csleeve.repository.CouponRepository;
import com.guomz.csleeve.repository.UserCouponRepository;
import com.guomz.csleeve.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CouponServiceImpl {

    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private UserCouponRepository userCouponRepository;

    /**
     * 根据分类找出可以领取的优惠券
     * @param categoryId
     * @return
     */
    public List<Coupon> getCouponsByCategoryId(Long categoryId){
        List<Coupon> couponList = couponRepository.findCouponListByCategoryId(categoryId, new Date());
        return couponList;
    }

    /**
     * 找出所有全场券
     * @return
     */
    public List<Coupon> getWholeStoreCoupons(){
        List<Coupon> couponList = couponRepository.findWholeStoreCouponList(true, new Date());
        return couponList;
    }

    /**
     * 找出可使用的优惠券
     * @param userId
     * @return
     */
    public List<Coupon> getActivedCoupon(Long userId){
        return couponRepository.findActivedCoupon(userId, new Date());
    }

    /**
     * 找出已使用的优惠券
     * @param userId
     * @return
     */
    public List<Coupon> getUsedCoupon(Long userId){
        return couponRepository.findUsedCoupon(userId, new Date());
    }

    /**
     * 找出过期的优惠券
     * @param userId
     * @return
     */
    public List<Coupon> getExpiredCoupon(Long userId){
        return couponRepository.findExpiredCoupon(userId, new Date());
    }

    /**
     * 用户领取优惠券
     * @param userId
     * @param couponId
     */
    public void collectCoupon(Long userId, Long couponId){
        //先检查优惠券和其所属的活动是否存在
        Optional<Coupon> coupon = couponRepository.findById(couponId);
        coupon.orElseThrow(()-> new NotFoundException(40002));
        Optional<Activity> activity = activityRepository.findFirstByCouponList(couponId);
        activity.orElseThrow(()-> new NotFoundException(40003));
        Date nowTime = new Date();
        //检查优惠券是否过期
        if(!CommonUtil.isInTimeLine(nowTime, activity.get().getStartTime(), activity.get().getEndTime())){
            throw new ParameterException(40004);
        }
        Optional<UserCoupon> userCouponOptional = userCouponRepository.findByUserIdAndCouponId(userId, couponId, CouponStatus.USED.getValue());
        //检查是否已被领取
        userCouponOptional.ifPresent((uc)-> new ParameterException(40005));
        UserCoupon userCoupon = new UserCoupon();
        userCoupon.setCouponId(couponId);
        userCoupon.setUserId(userId);
        userCoupon.setStatus(CouponStatus.AVAILABLE.getValue());
        userCoupon.setCreateTime(nowTime);
        userCoupon.setUpdateTime(nowTime);
        userCouponRepository.save(userCoupon);
    }
}
