package com.guomz.csleeve.service;

import com.guomz.csleeve.bo.MessageBo;
import com.guomz.csleeve.enums.CouponStatus;
import com.guomz.csleeve.exception.http.ParameterException;
import com.guomz.csleeve.model.UserCoupon;
import com.guomz.csleeve.repository.UserCouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 处理归还优惠券逻辑
 */
@Service
public class CouponBackServiceImpl {

    @Autowired
    private UserCouponRepository userCouponRepository;

    @Transactional
    public void couponBack(MessageBo messageBo){
        //判断用户是否有该优惠券使用记录
        Optional<UserCoupon> userCouponOptional = userCouponRepository.findByCouponByUserIdAndCouponIdAndOrderId(messageBo.getUserId(), messageBo.getCouponId(), messageBo.getOrderId(), CouponStatus.USED.getValue());
        userCouponOptional.orElseThrow(() -> new ParameterException(50012));
        //更改优惠券状态并将订单编号置空
        UserCoupon userCoupon = userCouponOptional.get();
        userCoupon.setStatus(CouponStatus.AVAILABLE.getValue());
        userCoupon.setOrderId(null);
        userCouponRepository.save(userCoupon);
    }
}
