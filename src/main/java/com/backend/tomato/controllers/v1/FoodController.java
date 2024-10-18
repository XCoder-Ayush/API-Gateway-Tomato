package com.backend.tomato.controllers.v1;

import com.backend.tomato.entitites.Food;
import com.backend.tomato.services.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<Food> addFoodItem(@RequestPart("product") Food food,
                                            @RequestPart(value = "image", required = false) MultipartFile image) {
        try{
            this.foodService.addFoodItem(food,image);
            return new ResponseEntity<>(food,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Food> updateFoodItem(@RequestPart("product") Food food,
                                               @RequestPart(value = "image", required = false) MultipartFile image) {
        try{
            this.foodService.updateFoodItem(food,image);
            return new ResponseEntity<>(food,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
