package com.backend.tomato.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class OTPMessageService {

    @Value("${twilio.account.sid}")
    private String twilioAccountSid;

    @Value("${twilio.account.sid}")
    private String twilioAuthToken;

    @Value("${twilio.account.phone}")
    private String twilioPhoneNumber;

    @Autowired
    private OTPGeneratorService otpGeneratorService;
    public String sendOTP(String recipientPhoneNumber) {
        Twilio.init(twilioAccountSid, twilioAuthToken);

        // Compose the message
        String messageBody = "Your OTP is: " + this.otpGeneratorService.generateOTP();

        // Send the message
        try{
            Message message = Message.creator(
                            new PhoneNumber(recipientPhoneNumber),
                            new PhoneNumber(twilioPhoneNumber),
                            messageBody)
                    .create();
            return "OTP Sent Successfully";
        }catch (Exception e){
            e.printStackTrace();
            return "Something Went Wrong";
        }
    }
}
