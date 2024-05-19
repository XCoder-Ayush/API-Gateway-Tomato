package com.backend.tomato.dao;

import com.backend.tomato.entitites.Address;
import com.backend.tomato.entitites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AddressDao extends JpaRepository<Address, UUID> {
    List<Address> findByUser(User user);
}
