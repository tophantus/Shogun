package com.estuamante.shogun.mappers;


import com.estuamante.shogun.dtos.CategoryTypeDto;
import com.estuamante.shogun.entities.CategoryType;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryTypeMapper {
    CategoryTypeDto toDto(CategoryType categoryType);
    CategoryType toEntity(CategoryTypeDto categoryTypeDto);

    List<CategoryTypeDto> toDtoList(List<CategoryType> categoryTypes);
    List<CategoryType> toEntityList(List<CategoryTypeDto> categoryTypeDtos);
}
