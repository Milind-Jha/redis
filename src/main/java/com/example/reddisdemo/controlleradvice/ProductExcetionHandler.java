package com.example.reddisdemo.controlleradvice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class ProductExcetionHandler {
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<String> productNotFound(HttpClientErrorException clientErrorException){
        String message = clientErrorException.getMessage();
        return new ResponseEntity<>(message,HttpStatus.NOT_FOUND);
    }
}

