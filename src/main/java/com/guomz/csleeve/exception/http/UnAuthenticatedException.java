package com.guomz.csleeve.exception.http;

public class UnAuthenticatedException extends HttpException {

    public UnAuthenticatedException(int code){
        this.code = code;
        this.httpStatusCode = 400;
    }
}
