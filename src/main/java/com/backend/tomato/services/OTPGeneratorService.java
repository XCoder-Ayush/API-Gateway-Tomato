package com.backend.tomato.services;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OTPGeneratorService {

    public String generateOTP() {
        int otpLength = 6;
        int minRange = (int) Math.pow(10, otpLength - 1); // 100000
        int maxRange = (int) Math.pow(10, otpLength) - 1; // 999999

        Random random = new Random();
        int randomNum = minRange + random.nextInt(maxRange - minRange + 1);

        return String.valueOf(randomNum);
    }
}
