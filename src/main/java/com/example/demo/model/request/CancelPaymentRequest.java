package com.example.demo.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CancelPaymentRequest {
    private String cancellationReason;
}
