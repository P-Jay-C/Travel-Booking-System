package com.amalitech.org.bookingservice.service;
import com.amalitech.org.bookingservice.entity.BookingOffer;
import com.amalitech.org.bookingservice.entity.Category;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    Category getCategoryById(ObjectId categoryId);

    Category createCategory(Category category);
    List<Category> getAllCategories();

}

