package com.springboot.bizconnect.domain.company.repository;

import com.springboot.bizconnect.entity.Company;
import com.springboot.bizconnect.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByName(String name);
}
