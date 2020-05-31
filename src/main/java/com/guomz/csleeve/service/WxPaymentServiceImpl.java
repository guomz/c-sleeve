package com.guomz.csleeve.service;

import com.guomz.csleeve.core.LocalUser;
import com.guomz.csleeve.enums.OrderStatus;
import com.guomz.csleeve.exception.http.ParameterException;
import com.guomz.csleeve.model.Order;
import com.guomz.csleeve.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 处理微信支付
 */
@Service
public class WxPaymentServiceImpl {

    @Autowired
    private OrderRepository orderRepository;
    @Value("${guo.pay-time-limit}")
    private Long payTimeLimit;

    /**
     * 进行微信支付
     * @param orderId 用户传入的订单id
     * @return
     */
    @Transactional
    public Map<String, String> preOrder(Long orderId){
        //判断订单是否存在以及是否过期
        Long userId = LocalUser.getUser().getId();
        Order order = orderRepository.findByUserIdAndId(userId, orderId);
        if (order == null){
            throw new ParameterException(50009);
        }
        if (order.needCancel(payTimeLimit)){
            throw new ParameterException(50010);
        }
        //微信支付流程省略
        //直接改变订单状态
        deal(userId, orderId);
        return null;
    }

    /**
     * 改变订单状态
     * @param orderId
     */
    private void deal(Long userId, Long orderId){
       Order order = orderRepository.findByUserIdAndId(userId, orderId);
       if (order == null){
           throw new ParameterException(50009);
       }
       if (OrderStatus.UNPAID.value() == order.getStatus() || OrderStatus.CANCELED.value() == order.getStatus()){
           order.setStatus(OrderStatus.PAID.value());
           orderRepository.save(order);
       }
    }
}
