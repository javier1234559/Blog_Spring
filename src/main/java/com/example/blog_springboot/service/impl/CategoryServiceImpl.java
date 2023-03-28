package com.example.blog_springboot.service.impl;


import com.example.blog_springboot.model.Category;
import com.example.blog_springboot.repository.CategoryRepository;
import com.example.blog_springboot.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(int id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(int id, Category category) {
        Category updatedCategory = categoryRepository.findById(id).orElse(null);
        if (updatedCategory != null) {
            updatedCategory.setName(category.getName());
            updatedCategory.setPost(category.getPost());
            categoryRepository.save(updatedCategory);
        }
        return updatedCategory;
    }

    @Override
    public void deleteCategory(int id) {
        categoryRepository.deleteById(id);
    }
}
