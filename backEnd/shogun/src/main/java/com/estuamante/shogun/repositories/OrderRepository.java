package com.estuamante.shogun.repositories;

import com.estuamante.shogun.auth.entities.User;
import com.estuamante.shogun.entities.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    @Query("SELECT o.id FROM Order o WHERE o.user.id = :userId")
    List<UUID> findOrderIdsByUserId(@Param("userId") UUID userId);

    @EntityGraph(attributePaths = {
            "orderItems",
            "address",
            "orderItems.product"
    })
    List<Order> findByUser(User user);

    @Query("""
        SELECT DISTINCT o FROM Order o
        JOIN FETCH o.orderItems i
        JOIN FETCH i.product p
        WHERE o.user = :user
    """)
    List<Order> findAllWithOrderItemsAndProductsByUser(@Param("user") User user);
}
