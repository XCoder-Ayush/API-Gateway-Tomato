package com.backend.tomato.dao;

import com.backend.tomato.entitites.OneTimePassword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OTPDao extends JpaRepository<OneTimePassword,String> {
}
