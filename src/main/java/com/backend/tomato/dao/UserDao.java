package com.backend.tomato.dao;

import com.backend.tomato.entitites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserDao extends JpaRepository<User,String> {
    public Optional<User> findByEmail(String email);
}
