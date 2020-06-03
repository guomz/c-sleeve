package com.guomz.csleeve.service;

import com.guomz.csleeve.bo.MessageBo;
import com.guomz.csleeve.enums.CouponStatus;
import com.guomz.csleeve.enums.OrderStatus;
import com.guomz.csleeve.exception.http.ParameterException;
import com.guomz.csleeve.model.Order;
import com.guomz.csleeve.model.UserCoupon;
import com.guomz.csleeve.repository.OrderRepository;
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
    private OrderRepository orderRepository;

    @Autowired
    private UserCouponRepository userCouponRepository;

    @Transactional
    public void couponBack(MessageBo messageBo){
        //没有使用优惠券则不报错直接返回
        if (messageBo.getCouponId() == null || messageBo.getCouponId() < 1){
            return;
        }
        //检查订单是否存在
        Optional<Order> orderOptional = orderRepository.findById(messageBo.getOrderId());
        orderOptional.orElseThrow(() -> new ParameterException(50009));
        Order order = orderOptional.get();
        //当订单处于待支付或已取消的状态时可归还优惠券
        if (order.getStatus() == OrderStatus.UNPAID.value() || order.getStatus() == OrderStatus.CANCELED.value()){
            Integer result = 0;
            //将优惠券状态改为可使用并置空关联的订单id
            result = userCouponRepository.couponBack(messageBo.getUserId(), messageBo.getCouponId(), messageBo.getOrderId(), CouponStatus.AVAILABLE.getValue());
            if (result != 1){
                throw new ParameterException(50012);
            }
        }
    }
}
