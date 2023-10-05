package com.backend.tomato.dao;

import com.backend.tomato.entitites.MyOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDao extends JpaRepository<MyOrder,String> {
}
