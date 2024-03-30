package com.example.demo.model.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreatePaymentRequest {
    /**
     * Mã đơn hàng(bắt buộc)
     */
    private Integer orderCode;

    /**
     * Số tiền thanh toán(bắt buộc)
     */
    private Integer amount;

    /**
     * Mô tả thanh toán, với tài khoản ngân hàng không phải liên kết qua payOS thì giới hạn ký tự là 9(bắt buộc)
     */
    private String description;

    /**
     * Email của người mua hàng. Thông tin dùng trong trường hợp tích hợp tạo hoá đơn điện tử.
     */
    private String buyerName;

    /**
     * Số điện thoại người mua hàng. Thông tin dùng trong trường hợp tích hợp tạo hoá đơn điện tử.
     */
    private String buyerPhone;

    /**
     * Địa chỉ của người mua hàng. Thông tin dùng trong trường hợp tích hợp tạo hoá đơn điện tử.
     */
    private String buyerAddress;

    /**
     * Danh sách sản phẩm
     */
    private List<Item> items;

    /**
     * URL nhận dữ liệu khi người dùng chọn Huỷ đơn hàng(bắt buộc)
     */
    private String cancelUrl;

    /**
     * URL nhận dữ liệu khi đơn hàng thanh toán thành công(bắt buộc)
     */
    private String returnUrl;

    /**
     * Thời gian hết hạn của link thanh toán, là Unix Timestamp và kiểu Int32
     */
    private long expiredAt;

    /**
     * Chữ ký kiểm tra thông tin không bị thay đổi trong qua trình chuyển dữ liệu từ hệ thống của bạn sang payOS. Bạn cần dùng checksum key từ Kênh thanh toán và HMAC_SHA256 để tạo signature và data theo định dạng được sort theo alphabet: amount=$amount&cancelUrl=$cancelUrl&description=$description&orderCode=$orderCode&returnUrl=$returnUrl.
     */
    private String signature;


    public static class Item {
        /**
         * Tên sản phẩm(bắt buộc)
         */
        private String name;

        /**
         * Số lượng(bắt buộc)
         */
        private Integer quantity;

        /**
         * Giá sản phẩm(bắt buộc)
         */
        private Integer price;
    }
}
