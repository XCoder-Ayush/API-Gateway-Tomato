package com.backend.tomato.dao;

import com.backend.tomato.entitites.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDao extends JpaRepository<Category,String> {
    @Query("SELECT COUNT(c) FROM Food f JOIN f.categories c WHERE f.id = :foodId")
    Integer getCategoryCount(@Param("foodId") String foodId);
    Integer countCategoriesById(String foodId);

    @Query("SELECT COUNT(f) FROM Food f JOIN f.categories c WHERE c.id = :categoryId")
    Integer countFoodItemsByCategoryId(@Param("categoryId") String categoryId);
}
