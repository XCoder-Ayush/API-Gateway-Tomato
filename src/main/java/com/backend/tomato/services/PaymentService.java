package com.backend.tomato.services;

import com.backend.tomato.dao.PaymentDao;
import com.backend.tomato.entitites.Payment;
import com.backend.tomato.entitites.User;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentDao paymentDao;

    @Autowired
    private UserService userService;

    @Value("${razorpay.public.key}")
    private String key;

    @Value("${razorpay.secret.key}")
    private String secret;

    private Logger logger= LoggerFactory.getLogger(PaymentService.class);

    public Order createRazorpayOrder(Map<String,Object> data) throws RazorpayException {
        BigDecimal amount= BigDecimal.valueOf(Integer.parseInt(data.get("amount").toString()));

        String userId=data.get("userId").toString();

        RazorpayClient razorpay =  new RazorpayClient(key,secret);

        try {

            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amount); // amount in the smallest currency unit
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "order_rcptid_11");
            /*
             Call To Razorpay Payment Server To Create An Order @RZP And Receive An Order Receipt Object:
            */
            Order order = razorpay.Orders.create(orderRequest);

            logger.info("Received Razorpay Order Object {} " + order);

            /*  Save In DB */
            Payment payment=new Payment();

            payment.setType("online");
            payment.setStatus("initiated");
            payment.setAmount(amount);

            User user=this.userService.fetchUserById(userId);
            payment.setUser(user);

            payment.setRzpOrderId(order.get("id"));

            this.paymentDao.save(payment);

            return order;
        }
        catch (RazorpayException e) {
            logger.info("Razorpay Exception Occured In Payment Service {} ", e.getMessage());
            throw e;
        }
        catch(Exception exception){
            logger.info("Exception Occured In Payment Service {} ", exception.getMessage());
            throw exception;
        }
    }
//    {
//        "entity": "event",
//            "account_id": "acc_MjcvpMJK03Xmbt",
//            "event": "payment.failed",
//            "contains": ["payment"],
//        "payload": {
//        "payment": {
//            "entity": {
//                "id": "pay_OCPrRSZGHfbseH",
//                        "entity": "payment",
//                        "amount": 55000,
//                        "currency": "INR",
//                        "status": "failed",
//                        "order_id": "order_OCPqncbWDNRzFU",
//                        "invoice_id": null,
//                        "international": false,
//                        "method": "card",
//                        "amount_refunded": 0,
//                        "refund_status": null,
//                        "captured": false,
//                        "description": "Transaction",
//                        "card_id": "card_OCPrRYdFPPX5up",
//                        "card": {
//                    "id": "card_OCPrRYdFPPX5up",
//                            "entity": "card",
//                            "name": "",
//                            "last4": "1111",
//                            "network": "Visa",
//                            "type": "debit",
//                            "issuer": null,
//                            "international": false,
//                            "emi": false,
//                            "sub_type": "consumer",
//                            "token_iin": null
//                },
//                "bank": null,
//                        "wallet": null,
//                        "vpa": null,
//                        "email": "void@razorpay.com",
//                        "contact": "+919874180842",
//                        "notes": {
//                    "address": "Tomato"
//                },
//                "fee": null,
//                        "tax": null,
//                        "error_code": "BAD_REQUEST_ERROR",
//                        "error_description": "Your payment has been cancelled. Try again or complete the payment later.",
//                        "error_source": "customer",
//                        "error_step": "payment_authentication",
//                        "error_reason": "payment_cancelled",
//                        "acquirer_data": {
//                    "auth_code": null
//                },
//                "created_at": 1716135634
//            }
//        }
//    },
//        "created_at": 1716135640
//    }
    public void updateRazorpayOrder(Map<String, Object> data) {
        try {
            Map<String,Object> payload= (Map<String, Object>) data.get("payload");
            Map<String,Object> payment= (Map<String, Object>) payload.get("payment");
            Map<String,Object> entity= (Map<String, Object>) payment.get("entity");

            String orderId= (String) entity.get("order_id");
            Optional<Payment> paymentOptional=this.paymentDao.findByRzpOrderId(orderId);
            if(paymentOptional.isPresent()){
                Payment paymentFetched=paymentOptional.get();
                paymentFetched.setStatus((String) entity.get("status"));
                paymentFetched.setRzpPayId((String) entity.get("id"));
                paymentFetched.setContact((String) entity.get("contact"));
                Payment paymentUpdated = this.paymentDao.save(paymentFetched);

                logger.info("Updated Payment Details {} ",paymentUpdated);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }

    }

    public Payment createCodOrder(Map<String, Object> data) {
        BigDecimal amount= BigDecimal.valueOf(Integer.parseInt(data.get("amount").toString()));
        String userId=data.get("userId").toString();
        try {
            /*  Save In DB */
            Payment payment=new Payment();

            payment.setType("cod");
            payment.setStatus("initiated");
            payment.setAmount(amount);

            User user=this.userService.fetchUserById(userId);
            payment.setUser(user);

            Payment savedPayment  = this.paymentDao.save(payment);
            return savedPayment;
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
