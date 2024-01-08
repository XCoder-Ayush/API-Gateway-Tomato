package com.backend.tomato.dao;

import com.backend.tomato.entitites.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoodRepository extends JpaRepository<Food,String> {
    @Query("SELECT COUNT(c) FROM Food f JOIN f.categories c WHERE f.id = :foodId")
    Integer getCategoryCount(@Param("foodId") String foodId);

}
