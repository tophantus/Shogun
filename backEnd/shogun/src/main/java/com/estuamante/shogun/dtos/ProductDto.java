package com.estuamante.shogun.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private String brand;
    private boolean isNewArrival;
    private UUID categoryId;
    private String thumbnail;
    private String slug;

    private UUID categoryTypeId;
//    private String categoryTypeName;
    private List<ProductVariantDto> productVariants;
    private List<ProductResourceDto> productResources;
}
