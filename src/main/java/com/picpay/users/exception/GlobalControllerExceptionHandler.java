package com.picpay.users.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {


    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> internalServerErrorHandler(Exception ex) {
        System.out.println(ex);
        return ExceptionResponse.InternalError();
    }
    @ExceptionHandler(value ={HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<?> HTTPMessageNotReadableExceptionHandle(Exception ex){
        System.out.println(ex);
        return ExceptionResponse.ValidationError();
    }
    @ExceptionHandler(value ={MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<?> MissingServletRequestParameterExceptionHandler(Exception ex){
        System.out.println(ex);
        return ExceptionResponse.ValidationError();
    }

    @ExceptionHandler(value ={NullPointerException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<?> NullPointerExceptionHandler(Exception ex){
        System.out.println(ex);
        return ExceptionResponse.ValidationError();
    }
}