package com.pranavpore.cartzy.service.category;

import com.pranavpore.cartzy.model.Category;

import java.util.List;

public interface ICategoryService {
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category addCategory(Category category);
    Category updateCategory(Category category, Long id);
    void deleteCategory(Category category);

    void deleteCategoryById(Long id);
}
