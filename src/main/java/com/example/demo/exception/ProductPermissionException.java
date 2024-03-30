package com.example.demo.exception;

public class ProductPermissionException extends RuntimeException{
    public ProductPermissionException(String msg){
        super(msg);
    }
}
