package com.estuamante.shogun.auth.repositories;

import com.estuamante.shogun.auth.entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;


public interface UserDetailsRepository extends JpaRepository<User, UUID> {
    User findByEmail(String username);
    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.authorities WHERE u.email = :email")
    Optional<User> findWithAuthoritiesByEmail(@Param("email") String email);

    // Fetch addresses
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.addresses WHERE u.email = :email")
    Optional<User> findWithAddressesByEmail(@Param("email") String email);
    void deleteByEmail(String username);
}
