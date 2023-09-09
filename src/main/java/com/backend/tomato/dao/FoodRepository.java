package com.backend.tomato.dao;

import com.backend.tomato.entitites.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<Food,Long> {

}
