package com.backend.tomato.dao;

import com.backend.tomato.entitites.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartDao extends JpaRepository<CartItems,String> {
    CartItems findByEmail(String email);

    void deleteByEmail(String email);

}
