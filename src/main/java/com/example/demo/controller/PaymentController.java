package com.example.demo.controller;

import com.example.demo.model.dto.*;
import com.example.demo.model.request.CancelPaymentRequest;
import com.example.demo.model.request.ConfirmWebhookRequest;
import com.example.demo.model.request.CreatePaymentRequest;
import com.example.demo.model.request.QRCodeRequest;
import com.example.demo.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Operation(summary = "Generate QR code", description = "Generate QR code")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/generateQR")
    public QRCodeDto generateQRCode(@RequestBody QRCodeRequest qrCodeRequest) throws URISyntaxException, IOException, InterruptedException {
        return paymentService.generateQRCode(qrCodeRequest);
    }

    @Operation(summary = "Create payment", description = "Create payment")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/create")
    public CreatePaymentDto createPayment(@RequestBody CreatePaymentRequest createPaymentRequest) throws URISyntaxException, IOException, InterruptedException, NoSuchAlgorithmException, InvalidKeyException {
        return paymentService.createPayment(createPaymentRequest);
    }

    @Operation(summary = "Confirm webhook", description = "Confirm webhook")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/confirmWebhook")
    public ConfirmWebhookDto confirmWebhook(@RequestBody ConfirmWebhookRequest confirmWebhookRequest) throws URISyntaxException, IOException, InterruptedException{
        return paymentService.confirmWebhook(confirmWebhookRequest);
    }

    @Operation(summary = "Cancel payment", description = "Cancel payment")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/cancel/{paymentId}")
    public CancelPaymentDto cancelPayment(@RequestBody CancelPaymentRequest cancelPaymentRequest, @PathVariable String paymentId) throws URISyntaxException, IOException, InterruptedException{
        return paymentService.cancelPayment(cancelPaymentRequest, paymentId);
    }

    @Operation(summary = "Get payment info", description = "Get payment ìnfo")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/get/{paymentId}")
    public PaymentInfoDto getPaymentInfo(@PathVariable String paymentId) throws URISyntaxException, IOException, InterruptedException{
        return paymentService.getPaymentInfo(paymentId);
    }
}
