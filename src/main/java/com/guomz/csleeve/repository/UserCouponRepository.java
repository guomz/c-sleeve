package com.guomz.csleeve.repository;

import com.guomz.csleeve.model.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {

    @Query("select uc\n" +
            "from UserCoupon uc\n" +
            "where\n" +
            "uc.userId = :userId and\n" +
            "uc.couponId = :couponId and\n" +
            "uc.status = :status\n")
    Optional<UserCoupon> findByUserIdAndCouponId(Long userId, Long couponId, Integer status);

    @Modifying
    @Query("update UserCoupon uc\n" +
            "set uc.status = :status, uc.orderId = :orderId\n" +
            "where\n" +
            "uc.userId = :userId and\n" +
            "uc.couponId = :couponId and\n" +
            "uc.orderId is null and\n" +
            "uc.status = 1")
    int writeOffCoupon(Long userId, Long couponId, Long orderId, Integer status);

    @Query("select uc\n" +
            "from UserCoupon uc\n" +
            "where\n" +
            "uc.userId = :userId and\n" +
            "uc.couponId = :couponId and\n" +
            "uc.status = :status and\n" +
            "uc.orderId = :orderId\n")
    Optional<UserCoupon> findByCouponByUserIdAndCouponIdAndOrderId(Long userId, Long couponId, Long orderId, Integer status);
}
