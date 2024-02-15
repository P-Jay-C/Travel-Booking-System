package com.amalitech.org.categoryservice.service;

import com.amalitech.org.categoryservice.Exceptions.ObjectNotFoundException;
import com.amalitech.org.categoryservice.entity.Category;
import com.amalitech.org.categoryservice.repository.CategoryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(ObjectId id) {
        return categoryRepository.findById(String.valueOf(id)).orElseThrow(() -> new ObjectNotFoundException("category", String.valueOf(id)));
    }

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }
}

