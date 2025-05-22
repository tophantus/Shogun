package com.estuamante.shogun.repositories;

import com.estuamante.shogun.entities.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {

    @Query("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.productResources WHERE p IN :products")
    List<Product> getAllWithResources(@Param("products") List<Product> products);

    @Query("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.productVariants WHERE p IN :products")
    List<Product> getAllWithVariants(@Param("products") List<Product> products);

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.productResources WHERE p.id = :id")
    Product findByIdWithResources(@Param("id") UUID id);

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.productVariants WHERE p.id = :id")
    Product findByIdWithVariants(@Param("id") UUID id);

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.productResources WHERE p.slug = :slug")
    Product findBySlugWithResources(@Param("slug") String slug);

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.productVariants WHERE p.slug = :slug")
    Product findBySlugWithVariants(@Param("slug") String slug);


}
