package com.pranavpore.cartzy.controller;

import com.pranavpore.cartzy.exceptions.ResourceAlreadyExistsException;
import com.pranavpore.cartzy.exceptions.ResourceNotFoundException;
import com.pranavpore.cartzy.model.Category;
import com.pranavpore.cartzy.response.APIResponse;
import com.pranavpore.cartzy.service.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Controller
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final ICategoryService categoryService;

    @GetMapping("/list")
    public ResponseEntity<APIResponse> getAllCategories() {
        try {
            List<Category> categoryList = categoryService.getAllCategories();
            return ResponseEntity.ok(new APIResponse("Found " + categoryList.size() + " categories",
                    categoryList));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new APIResponse("Internal Server Error",
                            null));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<APIResponse> addCategory(@RequestBody Category category) {
        try {
            Category theCategory = categoryService.addCategory(category);
            return ResponseEntity.ok(new APIResponse("Added Category", theCategory));
        } catch (ResourceAlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<APIResponse> getCategoryById(@PathVariable Long id) {
        try {
            Category theCategory = categoryService.getCategoryById(id);
            return ResponseEntity.ok(new APIResponse("Found Category", theCategory));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<APIResponse> getCategoryByName(@PathVariable String name) {
        try {
            Category theCategory = categoryService.getCategoryByName(name);
            return ResponseEntity.ok(new APIResponse("Found Category", theCategory));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<APIResponse> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategoryById(id);
            return ResponseEntity.ok(new APIResponse("Deleted Category", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<APIResponse> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        try {
            Category updatedCategory = categoryService.updateCategory(category, id);
            return ResponseEntity.ok(new APIResponse("Updated Category", updatedCategory));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }
}
