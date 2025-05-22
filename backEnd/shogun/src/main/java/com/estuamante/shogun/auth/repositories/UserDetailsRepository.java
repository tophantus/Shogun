package com.estuamante.shogun.auth.repositories;

import com.estuamante.shogun.auth.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserDetailsRepository extends JpaRepository<User, Long> {
    User findByEmail(String username);
    void deleteByEmail(String username);
}
