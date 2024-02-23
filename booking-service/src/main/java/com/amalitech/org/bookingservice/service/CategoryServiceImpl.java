package com.amalitech.org.bookingservice.service;
import com.amalitech.org.bookingservice.Exceptions.ObjectNotFoundException;
import com.amalitech.org.bookingservice.entity.Category;
import com.amalitech.org.bookingservice.repository.CategoryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}

