package com.example.demo.exception.exceptionHandler;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.response.ResponseApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class UserExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> userNotFoundExceptionHandler(UserNotFoundException ex){
        log.error(ex.getMessage());
        return new ResponseEntity<>(new ResponseApi<>("User not found", 404), HttpStatus.NOT_FOUND);
    }
}
