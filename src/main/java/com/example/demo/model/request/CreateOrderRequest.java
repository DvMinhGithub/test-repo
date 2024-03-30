package com.example.demo.model.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateOrderRequest {
    private List<Long> listCartItemId;
    private String address;
    private String phoneNumber;

}
