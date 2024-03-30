package com.example.demo.model.request;

import java.time.LocalDate;

import lombok.*;

@Getter
@Setter
public class UserRequest {

    private String name;

    private String phoneNumber;

    private String address;

    private Boolean gender;

    private LocalDate dob;

    private String avatar;
}
