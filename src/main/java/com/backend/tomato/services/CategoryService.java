package com.backend.tomato.services;

import com.backend.tomato.dao.CategoryDao;
import com.backend.tomato.dao.FoodRepository;
import com.backend.tomato.entitites.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private FoodRepository foodRepository;
    public Category addCategory(Category category){
        category.setId(UUID.randomUUID().toString());
        this.categoryDao.save(category);
        return category;
    }

    public List<Category> getCategories(){
        return this.categoryDao.findAll();
    }

    public Integer getCategoryCount(String categoryId) {
        return this.categoryDao.countFoodItemsByCategoryId(categoryId);
//        return this.foodRepository.getCategoryCount(id);
    }
}
