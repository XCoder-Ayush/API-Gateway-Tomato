package com.backend.tomato.services;

import com.backend.tomato.dao.OTPDao;
import com.backend.tomato.entitites.OneTimePassword;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.SendFailedException;
import java.util.Date;
import java.util.UUID;

@Service
public class OTPEmailService {

    private static final Long expiryInterval = 60L * 1000;//1 Minute

    @Autowired
    EmailService emailService;

    @Autowired
    OTPGeneratorService otpGeneratorService;

    @Autowired
    OTPDao otpDao;

    Logger logger= LoggerFactory.getLogger(OTPEmailService.class);

    public String sendEmail(String to){
        String subject="Tomato Verification";

        OneTimePassword oneTimePassword=new OneTimePassword();
        oneTimePassword.setId(UUID.randomUUID().toString());
        oneTimePassword.setOneTimePasswordCode(Integer.parseInt(this.otpGeneratorService.generateOTP()));
        oneTimePassword.setExpires(new Date(System.currentTimeMillis() + expiryInterval));

        String message="Your One Time Password(OTP) Is " + oneTimePassword.getOneTimePasswordCode();

        if(this.emailService.sendEmail(to,subject,message).equals("Invalid Email")){
            logger.info("Invalid Email");
            return "Invalid Email";
        }
        logger.info("Email Sent Successfully.");
        this.otpDao.save(oneTimePassword);
        return oneTimePassword.getId();
    }
}
