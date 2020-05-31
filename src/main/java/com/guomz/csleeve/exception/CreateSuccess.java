package com.guomz.csleeve.exception;

import com.guomz.csleeve.exception.http.HttpException;

public class CreateSuccess extends HttpException {
    public CreateSuccess(int code){
        this.code = code;
        this.httpStatusCode = 201;
    }
}
