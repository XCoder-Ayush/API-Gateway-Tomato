package com.backend.tomato.models;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CartItemId implements Serializable {
    private String email;
    private int foodId;
}
