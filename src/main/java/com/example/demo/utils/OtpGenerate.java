package com.example.demo.utils;

import java.util.Random;

public class OtpGenerate {
    public static String generateNumberOtp(int length) {
        String numbers = "0123456789";
        Random random = new Random();
        char[] otp = new char[length];
        for (int i = 0; i < length; i++) {
            otp[i] = numbers.charAt(random.nextInt(numbers.length()));
        }
        return new String(otp);
    }
}
