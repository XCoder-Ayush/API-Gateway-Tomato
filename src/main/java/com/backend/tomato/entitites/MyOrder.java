package com.backend.tomato.entitites;


import com.razorpay.Order;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="orders")
public class MyOrder {

    @Id
    private String orderId;
    private String userId;
    private Date timestamp;
    private String orderStatus;

    private int amount;

    @OneToMany
    private List<OrderItem> orderItems;

}
