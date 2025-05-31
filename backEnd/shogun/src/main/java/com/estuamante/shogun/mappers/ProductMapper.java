package com.estuamante.shogun.mappers;

import com.estuamante.shogun.dtos.ProductDto;
import com.estuamante.shogun.entities.Product;
import com.estuamante.shogun.entities.Resources;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductResourcesMapper.class, ProductVariantMapper.class})
public interface ProductMapper {
    @Mapping(target = "slug", source = "slug")
    Product toEntity(ProductDto productDto);

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryTypeId", source = "categoryType.id")

    @Mapping(target = "thumbnail", expression = "java(getThumbnail(product))")
    ProductDto toDto(Product product);

    default String getThumbnail(Product product) {
        if (product.getThumbnail() != null && !product.getThumbnail().isEmpty()) {
            return product.getThumbnail();
        }
        List<Resources> resources = product.getProductResources();
        if (resources == null) return null;
        return resources.stream()
                .filter(Resources::getIsPrimary)
                .map(Resources::getUrl)
                .findFirst()
                .orElse(null);
    }

    List<Product> toEntityList(List<ProductDto> productDtos);
    List<ProductDto> toDtoList(List<Product> products);
    
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProductFromDto(ProductDto productDto, @MappingTarget Product product);
}
