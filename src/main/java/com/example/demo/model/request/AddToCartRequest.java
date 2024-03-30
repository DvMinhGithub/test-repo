package com.example.demo.model.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddToCartRequest {
    private Integer quantity;

    private Long idProductAttribute;
}
