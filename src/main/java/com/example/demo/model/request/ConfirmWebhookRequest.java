package com.example.demo.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmWebhookRequest {
    /** Đường dẫn webhook nhận dữ liệu ngân hàng từ payOS của bạn */
    private String webhookUrl;
}
