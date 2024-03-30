package com.example.demo.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePaymentDto {
    /**
     * Mã lỗi
     */
    private String code;

    /**
     * Thông tin lỗi
     */
    private String desc;

    private CreatePaymentDtoData data;

    /**
     * Chữ ký kiểm tra thông tin không bị thay đổi trong qua trình payOS trả kết quả về cho hệ thống của bạn. Bạn cần dùng checksum key từ Kênh thanh toán và HMAC_SHA256 để tạo signature và so sánh signature từ kết quả trả về để kiểm tra dữ liệu.
     */
    private String signature;

    @Getter
    @Setter
    public static class CreatePaymentDtoData {
        /**
         * Mã định danh ngân hàng (thường gọi là BIN)
         */
        private String bin;

        /**
         * Số tài khoản ngân hàng thụ hưởng, là số tài khoản ảo nếu Cổng thanh toán liên kết với VietQR PRO
         */
        private String accountNumber;

        /**
         * Tên tài khoản ngân hàng
         */
        private String accountName;

        /**
         * Đơn vị tiền tệ
         */
        private String currency;

        /**
         * Mã link thanh toán
         */
        private String paymentLinkId;

        /**
         * Số tiền thanh toán
         */
        private Integer amount;

        /**
         * Mô tả thanh toán
         */
        private String description;

        /**
         * Mã đơn hàng từ cửa hàng
         */
        private Integer orderCode;

        /**
         * Trạng thái link thanh toán
         */
        private String status;

        /**
         * Link thanh toán
         */
        private String checkoutUrl;

        /**
         * Mã VietQR dạng text
         */
        private String qrCode;
    }
}
