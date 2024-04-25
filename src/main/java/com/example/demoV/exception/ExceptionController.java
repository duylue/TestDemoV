package com.example.demoV.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerNotFoundException(NotFoundException notFoundException){
        return new ErrorResponse(HttpStatus.NOT_FOUND,notFoundException.getMessage());
    }
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handlerBusinessException(BusinessException businessException){
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,businessException.getMessage());
    }
}
