package com.example.demo.model.request;

import lombok.*;

@Getter
@Setter
public class LoginRequest {
    private String email;

    private String password;
}
