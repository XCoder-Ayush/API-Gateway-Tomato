package com.backend.tomato.controllers;

import com.backend.tomato.entitites.MyOrder;
import com.backend.tomato.entitites.OrderItem;
import com.backend.tomato.entitites.Payment;
import com.backend.tomato.services.OrderService;
import com.backend.tomato.services.PaymentService;
import com.razorpay.*;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import org.json.HTTP;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/payments")
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

    @PostMapping("/hook")
    public ResponseEntity<Void> paymentsHook(@RequestBody(required = false)Map<String,Object> payload) {
        /*\
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

    @PostMapping("/create-order-cod")
    public ResponseEntity<String> createCodOrder(@RequestBody Map<String,Object> data) {
        try {
            Payment payment =this.paymentService.createCodOrder(data);
            return new ResponseEntity<>(payment.toString(),HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
