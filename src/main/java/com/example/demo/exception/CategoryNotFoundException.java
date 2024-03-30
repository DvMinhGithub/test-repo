package com.example.demo.exception;


public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(String msg){

        super(msg);
    }
}
