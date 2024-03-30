package com.example.demo.model.dto;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
public class ProductDto {
    private Long id;

    private String name;

    private String description;

    private Integer view = 0;

    private Integer sold = 0;

    private Integer discountPercent = 0;

    private Boolean hasDelete = false;

    private Boolean hasApproved = false;
}
