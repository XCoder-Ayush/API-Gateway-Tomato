package com.backend.tomato.dao;

import com.backend.tomato.entitites.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentDao extends JpaRepository<Payment, UUID> {
    Optional<Payment> findByRzpOrderId(String rzpOrderId);
}
