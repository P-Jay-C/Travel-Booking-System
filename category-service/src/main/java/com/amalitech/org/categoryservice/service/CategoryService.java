package com.amalitech.org.categoryservice.service;

import com.amalitech.org.categoryservice.entity.Category;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {
    Category getCategoryById(ObjectId categoryId);

    Category createCategory(Category category);
}

