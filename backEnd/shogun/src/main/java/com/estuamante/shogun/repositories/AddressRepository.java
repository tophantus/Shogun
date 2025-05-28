package com.estuamante.shogun.repositories;

import com.estuamante.shogun.entities.Address;
import jakarta.validation.constraints.AssertFalse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
}
