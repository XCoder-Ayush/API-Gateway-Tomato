package com.backend.tomato.controllers;

import com.backend.tomato.entitites.Food;
import com.backend.tomato.services.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/food")
@CrossOrigin("*")
public class FoodController {

    @Autowired
    FoodService foodService;

    @GetMapping("/get")
    public List<Food> getAllFoodItems(){
        return this.foodService.getAllFoodItems();
    }

    @PostMapping("/add")
    public Food addFoodItem(@RequestBody Food food) {
        return this.foodService.addFoodItem(food);
    }
}
