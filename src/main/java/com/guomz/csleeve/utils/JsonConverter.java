package com.guomz.csleeve.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guomz.csleeve.exception.http.ServerErrorException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * 为jpa指定序列化器，作用于指定字段，用于泛型集合类的序列化/反序列化
 * 需要在对应model类的字段上配合注解使用
 * @param <T>
 */
@Converter
public class JsonConverter<T> implements AttributeConverter<T, String> {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 序列化方法
     * @param t
     * @return
     */
    @Override
    public String convertToDatabaseColumn(T t) {
//        if(t == null){
//            return "";
//        }
        try {
            return objectMapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ServerErrorException(999);
        }
    }

    /**
     * 反序列化方法，支持各种泛型集合的反序列化
     * @param s
     * @return
     */
    @Override
    public T convertToEntityAttribute(String s) {
        if (s == null){
            return null;
        }
        try {
            return objectMapper.readValue(s, new TypeReference<T>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ServerErrorException(999);
        }
    }
}
