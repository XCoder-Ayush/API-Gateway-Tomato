package com.backend.tomato.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OTPEmailService {

    @Autowired
    EmailService emailService;

    @Autowired
    OTPGeneratorService otpGeneratorService;
    public String sendEmail(String to){
        String subject="Tomato Verification";
        String message="Your One Time Password(OTP) Is " + this.otpGeneratorService.generateOTP();
        return this.emailService.sendEmail(to,subject,message);
    }
}
