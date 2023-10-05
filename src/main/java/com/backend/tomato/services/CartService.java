package com.backend.tomato.services;

import com.backend.tomato.dao.CartDao;
import com.backend.tomato.dao.CartItemDao;
import com.backend.tomato.entitites.CartItems;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartDao cartDao;

    @Autowired
    private CartItemDao cartItemDao;
    public CartItems getCartItems(String email) {
        return this.cartDao.findByEmail(email);
    }

    public CartItems saveCartItems(CartItems cartItems) {
        return this.cartDao.save(cartItems);
    }

    @Transactional
    public void deleteItems(String email) {
        this.cartDao.deleteByEmail(email);
        this.cartItemDao.deleteByEmail(email);
    }
}
