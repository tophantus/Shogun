package com.estuamante.shogun.mappers;

import com.estuamante.shogun.dtos.ProductVariantDto;
import com.estuamante.shogun.entities.ProductVariant;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductVariantMapper {
    ProductVariant toEntity(ProductVariantDto productVariantDto);
    ProductVariantDto toDto(ProductVariant productVariant);

    List<ProductVariant> toEntityList(List<ProductVariantDto> productVariantDtos);
    List<ProductVariantDto> toDtoList(List<ProductVariant> productVariants);
}
