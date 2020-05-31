package com.guomz.csleeve.exception.http;

public class ParameterException extends HttpException{

    public ParameterException(Integer code){
        this.code = code;
        this.httpStatusCode = 400;
    }
}
