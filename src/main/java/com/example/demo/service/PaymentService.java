package com.example.demo.service;

import com.example.demo.model.dto.*;
import com.example.demo.model.request.CancelPaymentRequest;
import com.example.demo.model.request.ConfirmWebhookRequest;
import com.example.demo.model.request.CreatePaymentRequest;
import com.example.demo.model.request.QRCodeRequest;
import com.example.demo.utils.Signature;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

@Service
public class PaymentService {

    @Value("${VietQR.clientId}")
    private String clientId;

    @Value("${VietQR.apiKey}")
    private String apiKey;

    @Value("${PayOS.clientId}")
    private String payOSClientId;

    @Value("${PayOS.checkSumKey}")
    private String checksumKey;

    @Value("${PayOS.apiKey}")
    private String payOSApiKey;

    @Value("${PayOS.returnUrl}")
    private String returnUrl;

    @Value("${PayOS.cancelUrl}")
    private String cancelUrl;

    public QRCodeDto generateQRCode(QRCodeRequest qrCodeRequest) throws URISyntaxException, IOException, InterruptedException {
        Gson gson = new Gson();
        String jsonRequest = gson.toJson(qrCodeRequest);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI("https://api.vietqr.io/v2/generate"))
                .header("Content-Type", "application/json")
                .header("x-client-id", clientId)
                .header("x-api-key", apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        QRCodeDto qrCodeDto = gson.fromJson(response.body(), QRCodeDto.class);
        return qrCodeDto;
    }

    //Tạo link thanh toán
    public CreatePaymentDto createPayment(CreatePaymentRequest createPaymentRequest) throws URISyntaxException, IOException, InterruptedException, NoSuchAlgorithmException, InvalidKeyException {
        String dataFormat = String.format("amount=%s&cancelUrl=%s&description=%s&orderCode=%s&returnUrl=%s", createPaymentRequest.getAmount(), cancelUrl, createPaymentRequest.getDescription(), createPaymentRequest.getOrderCode(), returnUrl);
        createPaymentRequest.setSignature(Signature.HmacSignatureGenerate(checksumKey, dataFormat));
        createPaymentRequest.setReturnUrl(returnUrl);
        createPaymentRequest.setCancelUrl(cancelUrl);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        long unixTimeStamp = calendar.getTimeInMillis() / 1000L;
        createPaymentRequest.setExpiredAt(unixTimeStamp);

        Gson gson = new Gson();
        String jsonRequest = gson.toJson(createPaymentRequest);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI("https://api-merchant.payos.vn/v2/payment-requests"))
                .header("Content-Type", "application/json")
                .header("x-client-id", payOSClientId)
                .header("x-api-key", payOSApiKey)
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        CreatePaymentDto createPaymentDto = gson.fromJson(response.body(), CreatePaymentDto.class);
        return createPaymentDto;
    }


    //Kiểm tra và thêm hoặc cập nhật Webhook url cho kênh thanh toán
    public ConfirmWebhookDto confirmWebhook(ConfirmWebhookRequest confirmWebhookRequest) throws URISyntaxException, IOException, InterruptedException {
        Gson gson = new Gson();
        String jsonRequest = gson.toJson(confirmWebhookRequest);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI("https://api-merchant.payos.vn/confirm-webhook"))
                .header("Content-Type", "application/json")
                .header("x-client-id", payOSClientId)
                .header("x-api-key", payOSApiKey)
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        ConfirmWebhookDto confirmWebhookDto = gson.fromJson(response.body(), ConfirmWebhookDto.class);
        return confirmWebhookDto;
    }


    //Huỷ link thanh toán
    public CancelPaymentDto cancelPayment(CancelPaymentRequest cancelPaymentRequest, String paymentId) throws URISyntaxException, IOException, InterruptedException {
        /** paymentId: Mã đơn hàng của cửa hàng hoặc mã link thanh toán của payOS */
        String cancelPaymentUrl = String.format("https://api-merchant.payos.vn/v2/payment-requests/%s/cancel", paymentId);
        Gson gson = new Gson();
        String jsonRequest = gson.toJson(cancelPaymentRequest);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(cancelPaymentUrl))
                .header("Content-Type", "application/json")
                .header("x-client-id", payOSClientId)
                .header("x-api-key", payOSApiKey)
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        CancelPaymentDto cancelPaymentDto = gson.fromJson(response.body(), CancelPaymentDto.class);
        return cancelPaymentDto;
    }

    public PaymentInfoDto getPaymentInfo(String paymentId) throws URISyntaxException, IOException, InterruptedException {
        /** paymentId: Mã đơn hàng của cửa hàng hoặc mã link thanh toán của payOS */
        String getPaymentInfoUrl = String.format("https://api-merchant.payos.vn/v2/payment-requests/%s", paymentId);
        Gson gson = new Gson();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(getPaymentInfoUrl))
                .header("Content-Type", "application/json")
                .header("x-client-id", payOSClientId)
                .header("x-api-key", payOSApiKey)
                .GET()
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        PaymentInfoDto paymentInfoDto = gson.fromJson(response.body(), PaymentInfoDto.class);
        return paymentInfoDto;
    }
}
