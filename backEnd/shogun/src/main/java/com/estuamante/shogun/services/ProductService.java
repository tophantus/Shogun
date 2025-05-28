package com.estuamante.shogun.services;

import com.estuamante.shogun.dtos.ProductDto;
import com.estuamante.shogun.entities.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<ProductDto> getAllProduct(UUID categoryId, UUID categoryTypeId);
    Product addProduct(ProductDto productDto);
    Product updateProduct(UUID id, ProductDto productDto);

    ProductDto getProductBySlug(String slug);

    ProductDto getProductDtoById(UUID id);

    Product findProductById(UUID id);
}
