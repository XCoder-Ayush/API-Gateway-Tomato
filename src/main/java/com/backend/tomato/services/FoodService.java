package com.backend.tomato.services;

import com.backend.tomato.dao.FoodRepository;
import com.backend.tomato.entitites.Food;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {

    @Autowired
    FoodRepository foodRepository;

    public List<Food> getAllFoodItems(){
        List<Food> foodItems=this.foodRepository.findAll();
//        System.out.println("**********************************************************************************************************************************");
//        System.out.println(foodItems);

        for(Food food : foodItems){
            System.out.println(food.getTags());
        }
        return foodItems;
    }

    public Food addFoodItem(Food food) {
        System.out.println(food);
        return this.foodRepository.save(food);
    }
}
