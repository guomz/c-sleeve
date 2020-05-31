package com.guomz.csleeve.core;

import com.guomz.csleeve.exception.CreateSuccess;
import lombok.Getter;
import lombok.Setter;

/**
 * 用于返回错误信息，包含错误码、描述、访问路径等
 */
@Getter
@Setter
public class UnifyResponse {

    private Integer code;
    private String message;
    private String request;

    public UnifyResponse(Integer code, String message, String request) {
        this.code = code;
        this.message = message;
        this.request = request;
    }

    public UnifyResponse(){

    }


    @Override
    public String toString() {
        return "UnifyResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", request='" + request + '\'' +
                '}';
    }

    /**
     * 使用抛出异常的方式表示请求成功
     */
    public static void crreateSuccess(){
        throw new CreateSuccess(1);
    }
}
