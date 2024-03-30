package com.example.demo.controller;

import com.example.demo.model.request.CreateOrderRequest;
import com.example.demo.model.response.ResponseApi;
import com.example.demo.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Create order", description = "Create order")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/create")
    public ResponseEntity<ResponseApi<?>> createOrder(Principal principal, CreateOrderRequest createOrderRequest) {
        return orderService.createOrder(principal, createOrderRequest);
    }
}
