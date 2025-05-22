package com.estuamante.shogun.services;

import com.estuamante.shogun.dtos.CategoryDto;
import com.estuamante.shogun.dtos.CategoryTypeDto;
import com.estuamante.shogun.entities.Category;
import com.estuamante.shogun.entities.CategoryType;
import com.estuamante.shogun.exceptions.ResourcesNotFoundEx;
import com.estuamante.shogun.mappers.CategoryMapper;
import com.estuamante.shogun.mappers.CategoryTypeMapper;
import com.estuamante.shogun.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryDto getCategory(UUID id) {
        Optional<Category> category = categoryRepository.findById(id);
        return categoryMapper.toDto(category.orElse(null));
    }

    public Category createCategory(CategoryDto categoryDto) {
        Category category = categoryMapper.toEntity(categoryDto);//mapToEntity(categoryDto);
        //Update
        List<CategoryType> categoryTypes = category.getCategoryTypes();

        if (categoryTypes != null) {
            List<CategoryType> updatedCategoryTypes = categoryTypes
                    .stream()
                    .map(categoryType -> {
                        categoryType.setCategory(category);
                        return categoryType;
                    }).toList();
            category.setCategoryTypes(updatedCategoryTypes);
        }
        return categoryRepository.save(category);
    }

    public List<CategoryDto> getAllCategories() {
        List<Category> categories =  categoryRepository.findAllWithCategoryTypes();

        return categoryMapper.toDtoList(categories);
    }

    public Category updateCategory(UUID id, CategoryDto categoryDto) {
        Category category = categoryRepository.findByIdWithCategoryTypes(id)
                .orElseThrow(() -> new ResourcesNotFoundEx("Category not found with id " + id));

        categoryDto.setId(id);
        if (categoryDto.getCategoryTypes() != null) {
            category.getCategoryTypes().clear();
        }
        categoryMapper.updateCategoryFromDto(categoryDto, category);
        if (categoryDto.getCategoryTypes() != null) {
            List<CategoryType> updatedCategoryTypes = category.getCategoryTypes().stream()
                    .map(categoryType -> {
                        categoryType.setCategory(category);
                        return categoryType;
                    })
                    .toList();
            category.setCategoryTypes(updatedCategoryTypes);
        }

        return categoryRepository.save(category);
    }

    public void deleteCategory(UUID id) {
        categoryRepository.deleteById(id);
    }

    public Category getCategoryWithCategoryType(UUID id) {
        return categoryRepository.findByIdWithCategoryTypes(id)
                .orElseThrow(() -> new ResourcesNotFoundEx("Category not found with id " + id));
    }
//    private Category mapToEntity(CategoryDto categoryDto) {
//        Category category = Category.builder()
//                .code(categoryDto.getCode())
//                .name(categoryDto.getName())
//                .description(categoryDto.getDescription())
//                .build();
//        if (categoryDto.getCategoryTypes() != null) {
//            List<CategoryType> categoryTypeList = mapToCategoryTypeList(categoryDto.getCategoryTypes(), category);
//            category.setCategoryTypes(categoryTypeList);
//        }
//        return category;
//    }

//    private List<CategoryType> mapToCategoryTypeList(List<CategoryTypeDto> categoryTypes, Category category) {
//        return categoryTypes.stream().map(categoryTypeDto -> {
//            CategoryType categoryType = new CategoryType();
//            categoryType.setCode(categoryTypeDto.getCode());
//            categoryType.setName(categoryTypeDto.getName());
//            categoryType.setDescription(categoryTypeDto.getDescription());
//            categoryType.setCategory(category);
//            return categoryType;
//        }).collect(Collectors.toList());
//    }
}
