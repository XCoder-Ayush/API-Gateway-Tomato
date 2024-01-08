package com.backend.tomato.services;

import com.backend.tomato.dao.FoodRepository;
import com.backend.tomato.entitites.Food;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FoodService {

    @Autowired
    FoodRepository foodRepository;

    public List<Food> getAllFoodItems(){
        return this.foodRepository.findAll();
    }

    public Food addFoodItem(Food food) {
        food.setId(UUID.randomUUID().toString());
        return this.foodRepository.save(food);
    }

    public Food updateFoodItem(Food food) {
        Optional<Food> existingFoodItemOptional = this.foodRepository.findById(food.getId());
        if (existingFoodItemOptional.isPresent()) {
            Food existingFoodItem = existingFoodItemOptional.get();
            this.foodRepository.delete(existingFoodItem);
            return this.foodRepository.save(food);
        } else {
            // Handle the case where the food item with the given ID is not found
            throw new EntityNotFoundException("Food item not found");
        }
    }
}
