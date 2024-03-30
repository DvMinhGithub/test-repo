package com.example.demo.exception;

public class ShopNotFoundException extends RuntimeException {
    public ShopNotFoundException(String msg) {
        super(msg);
    }
}
