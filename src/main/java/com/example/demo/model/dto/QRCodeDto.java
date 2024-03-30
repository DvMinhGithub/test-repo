package com.example.demo.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QRCodeDto {
    private String code;
    private String desc;
    private QRCodeData data;

    @Getter
    @Setter
    public static class QRCodeData {
        private String acpId;
        private String accountName;
        private String qrCode;
        private String qrDataURL;
    }
}
