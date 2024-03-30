package com.example.demo.exception.exceptionHandler;

import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.exception.ProductPermissionException;
import com.example.demo.model.response.ResponseApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ProductExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<?> productNotFoundExceptionHandler(ProductNotFoundException ex){
        log.error(ex.getMessage());
        return new ResponseEntity<>(new ResponseApi<>("Product not found", 404), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductPermissionException.class)
    public ResponseEntity<?> productPermissionExceptionHandler(ProductPermissionException ex){
        log.error(ex.getMessage());
        return new ResponseEntity<>(new ResponseApi<>("User dont have permission to operation this product", 403), HttpStatus.FORBIDDEN);
    }

}
