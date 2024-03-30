package com.example.demo.controller;

import com.example.demo.model.dto.ShopDto;
import com.example.demo.model.request.ShopRequest;
import com.example.demo.model.response.ResponseApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.service.ShopService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/shop")
public class ShopController {
    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @Operation(summary = "Get list shop", description = "Get list shop")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/get")
    public ResponseEntity<ResponseApi<List<ShopDto>>> getListShops() {
        return shopService.getListShops();
    }

    @Operation(summary = "Create shop", description = "Create shop")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/create")
    public ResponseEntity<ResponseApi<?>> createShop(Principal principal, @RequestBody ShopRequest shopRequest) {
        return shopService.createShop(principal, shopRequest);
    }

    @Operation(summary = "Get shop information", description = "Get shop information")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/getShopInformation")
    public ResponseEntity<ResponseApi<ShopDto>> getShopInformation(Principal principal) {
        return shopService.getShopInformation(principal);
    }
}
