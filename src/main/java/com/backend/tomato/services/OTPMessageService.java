package com.backend.tomato.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class OTPMessageService {

    private String twilioAccountSid="AC56f9d156c564851853d8f72312c920af";
    private String twilioAuthToken="6e26b48686ee4cfdf0851397bbe98912";
    private String twilioPhoneNumber="+14844982895";

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
