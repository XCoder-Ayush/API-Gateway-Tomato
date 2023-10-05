package com.backend.tomato.entitites;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class OrderItem {

    @Id
    private String id;
    private String imageUrl;
    private String name;
    private int price;
    private int quantity;

}
