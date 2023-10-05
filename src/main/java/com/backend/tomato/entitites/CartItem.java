package com.backend.tomato.entitites;


import com.backend.tomato.models.CartItemId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@IdClass(CartItemId.class)
public class CartItem {

    @Id
    private String email;
    @Id
    private int foodId;
    private int quantity;

}
