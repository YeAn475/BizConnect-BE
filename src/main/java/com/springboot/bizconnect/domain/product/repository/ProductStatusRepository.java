package com.springboot.bizconnect.domain.product.repository;

import com.springboot.bizconnect.entity.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductStatusRepository extends JpaRepository<ProductStatus, Long> {
    Optional<ProductStatus> findByName(String name);
}
