package com.example.demo.controller;

import com.example.demo.entity.CartItem;
import com.example.demo.model.request.AddToCartRequest;
import com.example.demo.model.response.ResponseApi;
import com.example.demo.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/addToCart")
    public ResponseEntity<ResponseApi<?>> addToCart(Principal principal, @RequestBody AddToCartRequest addToCartRequest) {
        return cartService.addToCart(principal, addToCartRequest);
    }

    @GetMapping("/get")
    public  ResponseEntity<ResponseApi<List<CartItem>>> getCartInformation(Principal principal) {
        return cartService.getCartInformation(principal);
    }
}
