package com.example.demo.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyUserAccountRequest {
    private String email;
    private String otp;
}
