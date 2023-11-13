package com.backend.tomato.controllers;

import com.backend.tomato.models.EmailRequest;
import com.backend.tomato.services.EmailService;
import com.backend.tomato.services.OTPEmailService;
import com.backend.tomato.services.OTPMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/otp")
public class OTPController {


    @Autowired
    private EmailService emailService;

    @Autowired
    private OTPMessageService otpMessageService;

    @Autowired
    private OTPEmailService otpEmailService;
    @GetMapping("/email/{to}")
    public String sendEmail(@PathVariable String to){
        return this.otpEmailService.sendEmail(to);
    }
    @GetMapping("/phone")
    public String sendOtpToPhone(@RequestBody String phoneNumber){
        return this.otpMessageService.sendOTP(phoneNumber);
    }

}
