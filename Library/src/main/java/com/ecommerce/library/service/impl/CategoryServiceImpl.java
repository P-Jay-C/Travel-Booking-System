package com.ecommerce.library.service.impl;

import com.ecommerce.library.dto.CategoryDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.repository.CategoryRepository;
import com.ecommerce.library.service.CategoryService;
import com.ecommerce.library.utils.ImageUpload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ImageUpload imageUpload;
    @Override
    public Category save(CategoryDto categoryDto) {
            Category categorySave = new Category();
            categorySave.setName(categoryDto.getName());
            categorySave.setDescription(categoryDto.getDescription());
            categorySave.setCode(categoryDto.getCode());
            categorySave.setActivated(true);
            categorySave.setDeleted(false);
            categorySave.setIcon(categoryDto.getIcon());
            return categoryRepository.save(categorySave);
    }


    @Override
    public Category update(Category category) {
        Category categoryUpdate = categoryRepository.getReferenceById(category.getId());
        categoryUpdate.setName(category.getName());
        return categoryRepository.save(categoryUpdate);
    }

    @Override
    public List<Category> findAllByActivatedTrue() {
        return categoryRepository.findAllByActivatedTrue();
    }

    @Override
    public List<Category> findALl() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.setActivated(false);
            category.setDeleted(true);
            categoryRepository.save(category);
        }
    }

    @Override
    public void enableById(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);

        if(optionalCategory.isPresent()){
            Category category = optionalCategory.get();
            category.setActivated(true);
            category.setDeleted(false);
            categoryRepository.save(category);
        }

    }

    @Override
    public List<CategoryDto> getCategoriesAndSize() {
        List<CategoryDto> categories = categoryRepository.getCategoriesAndSize();
        return categories;
    }

}


