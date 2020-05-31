package com.guomz.csleeve.manager.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;

/**
 * redis监听配置类，用来管理装配redis事件监听器
 */
@Configuration
public class RedisListenerConfiguration {

    @Bean
    public RedisMessageListenerContainer getListenerContainer(RedisConnectionFactory connectionFactory){
        //创建连接容器
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        //放入redis连接
        container.setConnectionFactory(connectionFactory);
        //写入需要被监听的类型，即超时监听
        Topic topic = new PatternTopic("__keyevent@0__:expired");
        container.addMessageListener(new TopicMessageListener(), topic);
        return container;
    }
}
