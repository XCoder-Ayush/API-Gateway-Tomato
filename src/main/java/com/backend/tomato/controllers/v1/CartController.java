package com.backend.tomato.controllers.v1;

import com.backend.tomato.entitites.CartItems;
import com.backend.tomato.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    @GetMapping("/{email}")
    public CartItems getCartItems(@PathVariable String email){
        System.out.println(email);
        return this.cartService.getCartItems(email);
    }

    @PostMapping
    public CartItems saveCartItems(@RequestBody CartItems cartItems){
        System.out.println(cartItems);
        return this.cartService.saveCartItems(cartItems);
    }

    @DeleteMapping("/{email}")
    public void deleteCartItems(@PathVariable String email){
        this.cartService.deleteItems(email);
    }
}