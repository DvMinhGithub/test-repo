package com.example.demo.exception.exceptionHandler;

import com.example.demo.exception.ShopNotFoundException;
import com.example.demo.model.response.ResponseApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ShopExceptionHandler {

    @ExceptionHandler(ShopNotFoundException.class)
    public ResponseEntity<?> shopNotFoundExceptionHandler(ShopNotFoundException ex){
        log.error(ex.getMessage());
        return new ResponseEntity<>(new ResponseApi<>("Shop not found", 404), HttpStatus.NOT_FOUND);
    }
}
