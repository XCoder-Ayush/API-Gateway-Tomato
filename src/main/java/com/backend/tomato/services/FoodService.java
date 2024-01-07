package com.backend.tomato.services;

import com.backend.tomato.dao.FoodRepository;
import com.backend.tomato.entitites.Food;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FoodService {

    @Autowired
    FoodRepository foodRepository;

    public List<Food> getAllFoodItems(){
        return this.foodRepository.findAll();
    }

    public Food addFoodItem(Food food) {
        System.out.println(food);
        food.setId(UUID.randomUUID().toString());
        return this.foodRepository.save(food);
    }
}
