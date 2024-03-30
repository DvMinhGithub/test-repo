package com.example.demo.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmWebhookDto{
    /** Mã lỗi */
    private String code;

    /** Thông tin lỗi */
    private String desc;
}
