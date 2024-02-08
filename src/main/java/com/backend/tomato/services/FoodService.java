package com.backend.tomato.services;

import com.backend.tomato.dao.FoodRepository;
import com.backend.tomato.entitites.Food;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    private org.slf4j.Logger logger= (org.slf4j.Logger) LoggerFactory.getLogger(FoodService.class);
    public List<Food> getAllFoodItems(){
        return this.foodRepository.findAll();
    }

    public Food addFoodItem(Food food, MultipartFile image) throws IOException {
        Map data= this.cloudinaryService.uploadImage(image);
        String imageUrl = (String) data.get("secure_url");

        food.setId(UUID.randomUUID().toString());
        food.setImageUrl(imageUrl);

        return this.foodRepository.save(food);
    }

    public Food updateFoodItem(Food food,MultipartFile image) throws IOException {
        Optional<Food> existingFoodItemOptional = this.foodRepository.findById(food.getId());
        if (existingFoodItemOptional.isPresent()) {
            logger.info("Found Item");
            Food existingFoodItem = existingFoodItemOptional.get();
            this.foodRepository.delete(existingFoodItem);
            logger.info("Deleted Item");
            String imageUrl=food.getImageUrl();
            if(image!=null){
                Map data= this.cloudinaryService.uploadImage(image);
                imageUrl = (String) data.get("secure_url");
            }

            food.setImageUrl(imageUrl);

            return this.foodRepository.save(food);
        } else {
            // Handle the case where the food item with the given ID is not found
            throw new EntityNotFoundException("Food item not found");
        }
    }
}
