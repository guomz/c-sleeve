package com.guomz.csleeve.manager.redis;

import com.guomz.csleeve.bo.MessageBo;
import com.guomz.csleeve.service.CouponBackServiceImpl;
import com.guomz.csleeve.service.OrderCancelServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

/**
 * 监听redis发送的订阅信息
 * 在监听器配置类中随着container被加入到spring中listener也同样被spring所管理，所以可以注入其他被管理的bean
 */
@Component
public class TopicMessageListener implements MessageListener {

    @Autowired
    private OrderCancelServiceImpl orderCancelService;

    @Autowired
    private CouponBackServiceImpl couponBackService;

    @Override
    public void onMessage(Message message, byte[] bytes) {
        byte[] body = message.getBody();
        byte[] channel = message.getChannel();
        System.out.println(new String(body));
        System.out.println(new String(channel));
        //执行取消订单与归还优惠券的操作
        MessageBo messageBo = new MessageBo(new String(body));
        orderCancelService.cancelOrder(messageBo);
        couponBackService.couponBack(messageBo);
    }
}
