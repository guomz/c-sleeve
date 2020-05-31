package com.guomz.csleeve.manager.redis;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

/**
 * 监听redis发送的订阅信息
 */
public class TopicMessageListener implements MessageListener {
    @Override
    public void onMessage(Message message, byte[] bytes) {
        byte[] body = message.getBody();
        byte[] channel = message.getChannel();
        System.out.println(new String(body));
        System.out.println(new String(channel));
    }
}
