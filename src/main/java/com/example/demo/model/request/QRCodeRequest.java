package com.example.demo.model.request;

import lombok.*;

@Getter
@Setter
public class QRCodeRequest {
    /**
     * Số tài khoản ngân hàng thụ hưởng. Chỉ nhập số, tối thiểu 6 ký tự, tối đa 19 kí tự
     */
    private String accountNo;

    /**
     * Tên tài khoản ngân hàng. Nhập tiếng Việt không dấu, viết hoa, tối thiểu 5 ký tự, tối đa 50 kí tự, không chứa các ký tự đặc biệt.
     */
    private String accountName;

    /**
     * Mã định danh ngân hàng (thường gọi là BIN) 6 chữ số, quy đinh bởi ngân hàng nước
     */
    private Integer acqId;

    /**
     * Số tiền chuyển. Chỉ nhập số, tối đa 13 kí tự
     */
    private Integer amount;

    /**
     * Nội dung chuyển tiền. Nhập tiếng Việt không dấu, tối đa 25 ký tự. Không chứa các ký tự đặc biệt
     */
    private String addInfo;

    /**
     * Định dạng QR trả về
     */
    private String format;

    /**
     * Mẫu VietQR trả về
     */
    private String template;
}
