package com.guomz.csleeve.bo;

import lombok.Data;

/**
 * 用于接收redis发送的订阅消息
 */
@Data
public class MessageBo {

    private Long userId;

    private Long orderId;

    private Long couponId;
    //存入redis的key，包含以上三种信息
    private String key;

    public MessageBo(String key){
        this.key = key;
        this.parseKey(key);
    }

    /**
     * 解析key字段
     * @param key
     */
    private void parseKey(String key){
        String keyArr[] = key.split(",");
        this.userId = Long.valueOf(keyArr[0]);
        this.orderId = Long.valueOf(keyArr[1]);
        this.couponId = Long.valueOf(keyArr[2]);
    }
}
