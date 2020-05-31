package com.guomz.csleeve.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guomz.csleeve.exception.http.ServerErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JsonConverterVer2 {

    private static ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper om){
        JsonConverterVer2.objectMapper = om;
    }

    public static<T> String objectToJson(T t) {
        try {
            return objectMapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ServerErrorException(999);
        }
    }

    public static<T> T jsonToObejct(String json, TypeReference<T> tTypeReference){
        if (json == null){
            return null;
        }
        try {
            return objectMapper.readValue(json, tTypeReference);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ServerErrorException(999);
        }
    }
}
