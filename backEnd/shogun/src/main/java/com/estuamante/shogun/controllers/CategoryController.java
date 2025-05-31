package com.estuamante.shogun.controllers;

import com.estuamante.shogun.dtos.CategoryDto;
import com.estuamante.shogun.entities.Category;
import com.estuamante.shogun.repositories.CategoryRepository;
import com.estuamante.shogun.services.CategoryService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
@CrossOrigin
public class CategoryController {
    private CategoryService categoryService;
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable(value = "id", required = true ) UUID categoryID) {
        CategoryDto category = categoryService.getCategory(categoryID);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories(HttpServletResponse response) {
        List<CategoryDto> categories = categoryService.getAllCategories();
        response.setHeader("Content-Range", String.valueOf(categories.size()));
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDto categoryDto) {
        Category category = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(
            @PathVariable(name = "id", required = true) UUID id,
            @RequestBody CategoryDto categoryDto
    ) {
        Category updatedCategory = categoryService.updateCategory(id, categoryDto);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable(name = "id", required = true)UUID id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
