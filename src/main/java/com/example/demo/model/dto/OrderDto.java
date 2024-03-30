package com.example.demo.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class OrderDto {
    private Long id;
    private BigDecimal totalPrice;
    private String phoneNumber;
    private String address;
    private LocalDate createdAt;
    private String status;
    private List<ShopOrder> shopOrders;

    @Getter
    @Setter
    public static class ShopOrder{
        private Long shopId;
        private String nameShop;
        private List<ProductOrder> productOrders;
    }

    @Getter
    @Setter
    public static class ProductOrder{
        private Long shopId;
        private String nameShop;
        private List<ProductAttributeOrder> productAttributeOrders;
    }

    @Getter
    @Setter
    public static class ProductAttributeOrder{
        private Long productAttributeId;
        private String productAttributeName;
    }
}
