package com.guomz.csleeve.repository;

import com.guomz.csleeve.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    //根据分类找出能够领取的优惠券，使用当前时间做排查
    @Query("select c from Coupon c\n" +
            "join c.categoryList\n" +
            "join Activity a on a.id = c.activityId\n" +
            "where\n" +
            "c.id = :categoryId and\n" +
            "a.startTime <= :nowDate\n" +
            "and a.endTime >= :nowDate\n")
    List<Coupon> findCouponListByCategoryId(Long categoryId, Date nowDate);

    //找出全场券，也可以找出全部非全场券
    @Query("select c from Coupon c\n" +
            "join Activity a on a.id = c.activityId\n" +
            "where\n" +
            "c.wholeStore = :isWholeStore and\n" +
            "a.startTime <= :nowDate\n" +
            "and a.endTime >= :nowDate")
    List<Coupon> findWholeStoreCouponList(Boolean isWholeStore, Date nowDate);

    //找出可使用的优惠券
    @Query("select c from Coupon c\n" +
            "join UserCoupon uc\n" +
            "on uc.couponId = c.id\n" +
            "where\n" +
            "uc.userId = :userId\n" +
            "and uc.status = 1\n" +
            "and " +
            "c.startTime <= :nowTime\n" +
            "and c.endTime >= :nowTime\n" +
            "and uc.orderId is null")
    List<Coupon> findActivedCoupon(Long userId, Date nowTime);

    //找出已使用的优惠券
    @Query("select c from Coupon c\n" +
            "join UserCoupon uc\n" +
            "on uc.couponId = c.id\n" +
            "where\n" +
            "uc.userId = :userId\n" +
            "and uc.status = 2\n" +
            "and c.startTime <= :nowTime\n" +
            "and c.endTime <= :nowTime\n" +
            "and uc.orderId is not " +
            "null ")
    List<Coupon> findUsedCoupon(Long userId, Date nowTime);

    //找出过期的优惠券
    @Query("select c from Coupon c\n" +
            "join UserCoupon uc\n" +
            "on uc.couponId = c.id\n" +
            "where\n" +
            "uc.userId = :userId\n" +
            "and c.endTime < :nowTime\n" +
            "and uc.orderId is null\n" +
            "and uc.status <d> 2\n")
    List<Coupon> findExpiredCoupon(Long userId, Date nowTime);
}
