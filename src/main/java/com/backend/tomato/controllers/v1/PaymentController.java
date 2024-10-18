package com.backend.tomato.controllers.v1;

import com.backend.tomato.entitites.Payment;
import com.backend.tomato.services.PaymentService;
import com.razorpay.Order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/v1/payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    Logger logger= LoggerFactory.getLogger(PaymentController.class);

    @PostMapping("/create-order-rzp")
    public ResponseEntity<String> createRazorpayOrder(@RequestBody Map<String,Object> data) {
        try {
            Order order=this.paymentService.createRazorpayOrder(data);
            return new ResponseEntity<>(order.toString(),HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/create-order-cod")
    public ResponseEntity<Payment> createCodOrder(@RequestBody Map<String,Object> data) {
        try {
            Payment payment =this.paymentService.createCodOrder(data);
            return new ResponseEntity<>(payment,HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/hook")
    public ResponseEntity<Void> paymentsHook(@RequestBody(required = false)Map<String,Object> payload) {
        /*
           This route is a Webhook which is being called by Razorpay Server.
         */
        logger.info("Webhook Called");
        logger.info("Payload {} ",payload);
        try{
            this.paymentService.updateRazorpayOrder(payload);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
