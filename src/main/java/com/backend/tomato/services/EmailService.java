package com.backend.tomato.services;
import com.backend.tomato.models.EmailRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;
import javax.mail.*;
import javax.mail.event.TransportEvent;
import javax.mail.event.TransportListener;
import javax.mail.internet.*;
@Service
public class EmailService implements TransportListener {

    @Value("${account.email}")
    private String username;

    @Value("${account.password}")
    private String password;
    public String sendEmail(String to, String subject, String messageBody) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        session.setDebug(true);
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
//            message.setText(messageBody);
            message.setContent(messageBody,"text/html");
            Transport transport = session.getTransport("smtp");
            transport.addTransportListener(this);

            transport.connect(username, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

//            Transport.send(message);
//            System.out.println("Email Done From Email Service");

            return "Email Sent Successfully";

        } catch (Exception e) {
            e.printStackTrace();
            return "Invalid Email";
        }
    }

    @Override
    public void messageDelivered(TransportEvent transportEvent) {
        System.out.println("Message delivered successfully to all recipients.");
    }

    @Override
    public void messageNotDelivered(TransportEvent transportEvent) {
        System.out.println("Message not delivered to some or all recipients.");
    }

    @Override
    public void messagePartiallyDelivered(TransportEvent transportEvent) {
        System.out.println("Message partially delivered.");
    }
}
