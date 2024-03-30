package com.example.demo.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CancelPaymentDto {
    /**
     * Mã lỗi
     */
    private String code;

    /**
     * Thông tin lỗi
     */
    private String desc;

    private CancelPaymentDtoData data;

    /**
     *
     */
    private String signature;

    @Getter
    @Setter
    public static class CancelPaymentDtoData {
        /**
         * Mã link thanh toán
         */
        private String id;

        /**
         * Mã đơn hàng từ cửa hàng
         */
        private Integer orderCode;

        /**
         * Số tiền thanh toán
         */
        private Integer amount;

        /**
         * Số tiền đã thanh toán
         */
        private Integer amountPaid;

        /**
         * Số tiền KH cần thanh toán thêm
         */
        private Integer amountRemaining;

        /**
         * Trạng thái link thanh toán
         */
        private String status;

        /**
         * Thời gian khởi tạo link
         */
        private String createdAt;

        /**
         * Danh sách giao dịch thanh toán đơn hàng
         */
        private List<CancelPaymentDtoTransaction> transactions;

        private String canceledAt;

        private String cancellationReason;
    }

    @Getter
    @Setter
    public static class CancelPaymentDtoTransaction {
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
