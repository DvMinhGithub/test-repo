package com.example.demo.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebhookRequest {
    /**
     * Mã lỗi
     */
    private String code;

    /**
     * Thông tin lỗi
     */
    private String desc;

    /**
     * Chữ kí để kiểm tra thông tin
     */
    private String signature;

    private WebhookRequestData data;

    @Getter
    @Setter
    public static class WebhookRequestData {
        /**
         * Mã đơn hàng từ cửa hàng
         */
        private Integer orderCode;

        /**
         * Số tiền thanh toán
         */
        private Integer amount;

        /**
         * Mô tả thanh toán
         */
        private String description;

        /**
         * Số tài khoản của cửa hàng
         */
        private String accountNumber;

        /**
         * Mã tham chiếu giao dịch, dùng để tra soát với ngân hàng
         */
        private String reference;

        /**
         * Ngày giờ giao dịch thực hiện thành công
         */
        private String transactionDateTime;

        /**
         * Đơn vị tiền tệ
         */
        private String currency;

        /**
         * Mã link thanh toán
         */
        private String paymentLinkId;

        /**
         * Mã lỗi của giao dịch
         */
        private String code;

        /**
         * Thông tin lỗi của giao dịch
         */
        private String desc;

        /**
         * Mã ngân hàng của khách hàng dùng chuyển khoản
         */
        private String counterAccountBankId;

        /**
         * Tên ngân hàng của khách hàng dùng chuyển khoản
         */
        private String counterAccountBankName;

        /**
         * Tên tài khoản của khách hàng
         */
        private String counterAccountName;

        /**
         * Số tài khoản của khách hàng
         */
        private String counterAccountNumber;

        /**
         * Tên tài khoản ảo
         */
        private String virtualAccountName;

        /**
         * Số tài khoản ảo
         */
        private String virtualAccountNumber;
    }

}
