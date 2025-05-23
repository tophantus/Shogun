package com.estuamante.shogun.specification;

import com.estuamante.shogun.entities.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class ProductSpecification {
    public static Specification<Product> hasCategoryId(UUID categoryId) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("category").get("id"),categoryId));
    }

    public static Specification<Product> hasCategoryTypeId(UUID categoryTypeId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("categoryType").get("id"), categoryTypeId);
    }
}
