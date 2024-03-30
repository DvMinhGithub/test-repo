package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.model.response.ResponseApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.request.UserRequest;
import com.example.demo.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Update user", description = "Update user")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/update")
    public ResponseEntity<ResponseApi<?>> updateUser(Principal principal, @RequestBody UserRequest userRequest) {
        return userService.updateUser(principal, userRequest);
    }

    @Operation(summary = "Get user", description = "Get user")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/get")
    public ResponseEntity<ResponseApi<User>> getUser(Principal principal) {
        return userService.getProfile(principal);
    }
}
