package com.estuamante.shogun.services;

import com.estuamante.shogun.dtos.ProductDto;
import com.estuamante.shogun.entities.*;
import com.estuamante.shogun.exceptions.ResourcesNotFoundEx;
import com.estuamante.shogun.mappers.CategoryMapper;
import com.estuamante.shogun.mappers.ProductMapper;
import com.estuamante.shogun.repositories.ProductRepository;
import com.estuamante.shogun.specification.ProductSpecification;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;

    private final CategoryService categoryService;

    private final ProductMapper productMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> getAllProduct(UUID categoryId, UUID categoryTypeId) {
        Specification<Product> productSpecification = Specification.where(null);
        if (categoryId != null) {
            productSpecification = productSpecification.and(ProductSpecification.hasCategoryId(categoryId));
        }
        if (categoryTypeId != null) {
            productSpecification = productSpecification.and(ProductSpecification.hasCategoryTypeId(categoryTypeId));
        }


        List<Product> products = productRepository.findAll(productSpecification);

        if (!products.isEmpty()) {
            productRepository.getAllWithResources(products);
            productRepository.getAllWithVariants(products);
        }
        return productMapper.toDtoList(products);
    }

    @Override
    public Product addProduct(ProductDto productDto) {
        Product product = mapToProductEntity(productDto);
        return productRepository.save(product);
    }
    @Override
    public Product updateProduct(UUID id, ProductDto productDto) {
        Product updatedProduct = updateProductFromDto(id, productDto);
        return productRepository.save(updatedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDto getProductBySlug(String slug) {
        Product product = productRepository.findBySlugWithResources(slug);
        if (product == null) {
            throw new ResourcesNotFoundEx("Product not found!");
        } else {
            productRepository.findBySlugWithVariants(slug);
        }
        return productMapper.toDto(product);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDto getProductDtoById(UUID id) {
        Product product = productRepository.findByIdWithResources(id);
        if (product != null) {
            productRepository.findByIdWithVariants(id);
        } else {
            throw new ResourcesNotFoundEx("Product not found");
        }
        return productMapper.toDto(product);
    }

    @Override
    @Transactional(readOnly = true)
    public Product findProductById(UUID id) {
        Product product = productRepository.findByIdWithResources(id);
        if (product != null) {
            product = productRepository.findByIdWithVariants(id);
        } else {
            throw new ResourcesNotFoundEx("Product not found");
        }
        return product;
    }
    //

    public List<Product> fetchProductVariantsForProducts(List<Product> products) {
        return productRepository.fetchProductVariantsForProducts(products);
    }

    //
    public List<Product> fetchProductResourcesForProducts(List<Product> products) {
        return productRepository.fetchProductResourcesForProducts(products);
    }

    @Transactional(readOnly = true)
    @Override
    public void fetchAllDetailsForProducts(List<Product> products) {
        fetchProductVariantsForProducts(products);
        fetchProductResourcesForProducts(products);
    }

    @Transactional
    public Product updateProductFromDto(UUID id, ProductDto productDto) {
        Product product = productRepository.findByIdWithResources(id);
        if (product == null) {
            throw new ResourcesNotFoundEx("Product not found");
        }
        Product productWithVariants = productRepository.findByIdWithVariants(id);

        product.setProductVariants(productWithVariants.getProductVariants());

        productDto.setId(id);
        if (productDto.getProductResources() != null) {
            product.getProductResources().clear();
        }
        if (productDto.getProductVariants() != null) {
            product.getProductVariants().clear();
        }
        productMapper.updateProductFromDto(productDto, product);
        if (productDto.getProductResources() != null) {
            List<Resources> productResources = product.getProductResources();
            if (productResources != null) {
                productResources.forEach(resources -> resources.setProduct(product));
                product.setProductResources(productResources);
            }
        }
        if (productDto.getProductVariants() != null) {
            List<ProductVariant> productVariants = product.getProductVariants();
            if (productVariants != null) {
                productVariants.forEach(variant -> variant.setProduct(product));
                product.setProductVariants(productVariants);
            }
        }
        if (productDto.getCategoryId() != null) {
            Category category = categoryService.getCategoryWithCategoryType(productDto.getCategoryId());
            //set category and categoryType
            if (category != null) {
                product.setCategory(category);
                UUID categoryTypeId = productDto.getCategoryTypeId();
                CategoryType categoryType = category.getCategoryTypes()
                        .stream()
                        .filter( categoryType1 ->
                                categoryType1.getId().equals(categoryTypeId)
                        ).findFirst().orElse(null);
                product.setCategoryType(categoryType);
            }
        }
        return product;
    }

    private Product mapToProductEntity(ProductDto productDto) {
        Product product = productMapper.toEntity(productDto);
        Category category = categoryService.getCategoryWithCategoryType(productDto.getCategoryId());
        //set category and categoryType
        if (category != null) {
            product.setCategory(category);
            UUID categoryTypeId = productDto.getCategoryTypeId();
            CategoryType categoryType = category.getCategoryTypes()
                    .stream()
                    .filter( categoryType1 ->
                        categoryType1.getId().equals(categoryTypeId)
                    ).findFirst().orElse(null);
            product.setCategoryType(categoryType);
        }
        //set productVariant and productResource
        List<ProductVariant> productVariants = product.getProductVariants();
        if (productVariants != null) {
            productVariants.forEach(variant -> variant.setProduct(product));
            product.setProductVariants(productVariants);
        }
        List<Resources> productResources = product.getProductResources();
        if (productResources != null) {
            productResources.forEach(resources -> resources.setProduct(product));
            product.setProductResources(productResources);
        }
        return product;
    }
}
