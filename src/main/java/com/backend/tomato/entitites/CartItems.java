package com.backend.tomato.entitites;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class CartItems {
    @Id
    private String email;

    @OneToMany(cascade = CascadeType.ALL)
    private List<CartItem> cartItemList;

}
