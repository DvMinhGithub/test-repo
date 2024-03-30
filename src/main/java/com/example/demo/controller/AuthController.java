package com.example.demo.controller;

import com.example.demo.model.request.VerifyUserAccountRequest;
import com.example.demo.model.request.WebhookRequest;
import com.example.demo.model.response.ResponseApi;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.request.LoginRequest;
import com.example.demo.model.request.RegisterRequest;
import com.example.demo.service.AuthService;

@RestController
@RequestMapping("/")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseApi<?>> register(@RequestBody @Valid RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseApi<?>> login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/verifyUserAccount")
    public ResponseEntity<ResponseApi<?>> verifyUserAccount(@RequestBody VerifyUserAccountRequest verifyUserAccountRequest) {
        return authService.verifyUserAccount(verifyUserAccountRequest);
    }

    @PostMapping("/getWebhookRequest")
    public ResponseEntity<?> getWebhookRequest(@RequestBody WebhookRequest webhookRequest) {
        System.out.println(webhookRequest);
        return ResponseEntity.ok("Done");
    }
}
