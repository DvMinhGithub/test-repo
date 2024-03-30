package com.example.demo.controller;

import com.example.demo.model.request.MailRequest;
import com.example.demo.model.response.ResponseApi;
import com.example.demo.service.MailService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class MailController {
    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @Operation(summary = "Send email with text message", description = "Send email with text message")
    @PostMapping("/sendTextMessage")
    public ResponseEntity<ResponseApi<?>> sendTextMessage(@RequestBody MailRequest mailRequest) {
        return mailService.sendTextMessage(mailRequest);
    }

    @Operation(summary = "Send email with attachment", description = "Send email with attachment")
    @PostMapping("/sendMessageWithAttachment")
    public ResponseEntity<ResponseApi<?>> sendMessageWithAttachment(@RequestBody MailRequest mailRequest){
        return mailService.sendCustomMessageWithAttachment(mailRequest);
    }
}
