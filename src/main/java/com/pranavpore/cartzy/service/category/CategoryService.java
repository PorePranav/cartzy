package com.pranavpore.cartzy.service.category;

import com.pranavpore.cartzy.exceptions.ResourceAlreadyExistsException;
import com.pranavpore.cartzy.exceptions.ResourceNotFoundException;
import com.pranavpore.cartzy.model.Category;
import com.pranavpore.cartzy.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        return Optional
                .of(category)
                .filter(c -> !categoryRepository.existsByName(c.getName()))
                .map(categoryRepository::save)
                .orElseThrow(() -> new ResourceAlreadyExistsException("Category " + category.getName() + " already exists"));

    }

    @Override
    public Category updateCategory(Category category, Long id) {
       return Optional
               .ofNullable(getCategoryById(id))
               .map(oldCategory -> {
                   oldCategory.setName(category.getName());
                   return categoryRepository.save(oldCategory);
               })
               .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    @Override
    public void deleteCategory(Category category) {
        categoryRepository
                .findById(category.getId())
                .ifPresentOrElse(categoryRepository::delete, () -> {
                    throw new ResourceNotFoundException("Category not found");
                });
    }

    @Override
    public void deleteCategoryById(Long id) {
        Category theCategory = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        categoryRepository.delete(theCategory);
    }
}
