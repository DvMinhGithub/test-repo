package com.example.demo.model.request;

import java.math.BigDecimal;
import java.util.List;

import com.example.demo.entity.ProductAttribute;
import lombok.*;

@Getter
@Setter
public class ProductRequest {

    private String name;

    private String description;

    private Integer discountPercent;

    private BigDecimal price;

    private List<Long> categoryIds;

    private List<ProductAttributeRequest> listAttribute;

    @Data
    public static class ProductAttributeRequest{
        private String size;
        private String color;
        private String image_url;
        private Integer quantity;
        private BigDecimal price;
    }
}
