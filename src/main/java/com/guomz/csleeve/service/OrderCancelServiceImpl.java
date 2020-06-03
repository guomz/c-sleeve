package com.guomz.csleeve.service;

import com.guomz.csleeve.bo.MessageBo;
import com.guomz.csleeve.enums.OrderStatus;
import com.guomz.csleeve.exception.http.ParameterException;
import com.guomz.csleeve.model.Order;
import com.guomz.csleeve.model.OrderSku;
import com.guomz.csleeve.repository.OrderRepository;
import com.guomz.csleeve.repository.SkuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 处理取消订单的业务
 */
@Service
public class OrderCancelServiceImpl {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private SkuRepository skuRepository;

    @Transactional
    public void cancelOrder(MessageBo messageBo){
        //订单编号不合法
        if (messageBo.getOrderId() < 1){
            throw new ParameterException(50009);
        }
        //判断订单是否存在 取出订单
        Optional<Order> orderOptional = orderRepository.findById(messageBo.getOrderId());
        orderOptional.orElseThrow(() -> new ParameterException(50009));
        Order order = orderOptional.get();
        if (!order.getStatus().equals(OrderStatus.UNPAID)){
            throw new ParameterException(50011);
        }
        //归还库存并更改订单状态
        skuCountReturn(order.getSnapItems());
        order.setStatus(OrderStatus.CANCELED.value());
        orderRepository.save(order);
    }

    /**
     * 归还库存
     * @param orderSkuList
     */
    private void skuCountReturn(List<OrderSku> orderSkuList){
        orderSkuList.stream().forEach(orderSku -> {
            skuRepository.addStockById(orderSku.getId(), orderSku.getCount());
        });
    }
}
