package com.example.demo.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseApi<T> {
    private String message;

    private Integer code;

    private T data;

    public ResponseApi(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public ResponseApi(String message, int code, T data) {
        this.message = message;
        this.code = code;
        this.data = data;
    }
}
