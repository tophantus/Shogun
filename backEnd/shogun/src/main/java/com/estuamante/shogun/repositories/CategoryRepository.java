package com.estuamante.shogun.repositories;

import com.estuamante.shogun.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    @Query("SELECT DISTINCT c FROM Category c LEFT JOIN FETCH c.categoryTypes")
    List<Category> findAllWithCategoryTypes();

    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.categoryTypes WHERE c.id = :id")
    Optional<Category> findByIdWithCategoryTypes(@Param("id") UUID id);
}
