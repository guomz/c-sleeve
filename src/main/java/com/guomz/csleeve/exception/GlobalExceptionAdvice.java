package com.guomz.csleeve.exception;

import com.guomz.csleeve.core.UnifyResponse;
import com.guomz.csleeve.core.configuration.ExceptionCodeConfiguration;
import com.guomz.csleeve.exception.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * 异常处理
 */
@ControllerAdvice
public class GlobalExceptionAdvice {

    @Autowired
    private ExceptionCodeConfiguration codeConfiguration;

    //处理未知异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public UnifyResponse handleException(HttpServletRequest request, Exception e){
        String method = request.getMethod();
        String requestUrl = request.getRequestURI();
        System.out.println(method);
        System.out.println(requestUrl);
        System.out.println(e);
        UnifyResponse unifyResponse = new UnifyResponse(9999, "服务器异常", requestUrl + " " + method);
        return unifyResponse;
    }

    //处理已知http异常，动态决定http错误码
    @ExceptionHandler(HttpException.class)
    public ResponseEntity handleHttpException(HttpServletRequest request, HttpException e){
        String method = request.getMethod();
        String requestUrl = request.getRequestURI();
        UnifyResponse unifyResponse = new UnifyResponse(e.getCode(), codeConfiguration.getMessage(e.getCode()), requestUrl + " " + method);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpStatus httpStatus = HttpStatus.resolve(e.getHttpStatusCode());
        ResponseEntity<UnifyResponse> responseEntity = new ResponseEntity(unifyResponse, headers, httpStatus);
        return responseEntity;
    }
}
