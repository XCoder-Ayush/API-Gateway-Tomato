package com.backend.tomato.controllers;

import com.backend.tomato.entitites.Food;
import com.backend.tomato.services.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    public ResponseEntity<Food> addFoodItem(@RequestBody Food food) {
        try{
            this.foodService.addFoodItem(food);
            return new ResponseEntity<>(food,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Food> updateFoodItem(@RequestBody Food food) {
        try{
            this.foodService.updateFoodItem(food);
            return new ResponseEntity<>(food,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
