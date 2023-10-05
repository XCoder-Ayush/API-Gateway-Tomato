package com.backend.tomato.dao;

import com.backend.tomato.entitites.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemDao extends JpaRepository<CartItem,String> {
    void deleteByEmail(String email);

}
