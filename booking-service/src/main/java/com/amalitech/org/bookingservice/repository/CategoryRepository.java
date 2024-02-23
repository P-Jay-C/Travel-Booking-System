package com.amalitech.org.bookingservice.repository;

import com.amalitech.org.bookingservice.entity.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {
}