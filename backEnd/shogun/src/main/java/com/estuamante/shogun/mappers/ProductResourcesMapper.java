package com.estuamante.shogun.mappers;

import com.estuamante.shogun.dtos.ProductResourceDto;
import com.estuamante.shogun.entities.Resources;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductResourcesMapper {
    Resources toEntity(ProductResourceDto productResourceDto);
    ProductResourceDto toDto(Resources resources);

    List<Resources> toEntityList(List<ProductResourceDto> productResourceDtos);
    List<ProductResourceDto> toDtoList(List<Resources> resources);
}
