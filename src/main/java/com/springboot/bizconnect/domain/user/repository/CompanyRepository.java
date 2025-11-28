package com.springboot.bizconnect.domain.user.repository;

import com.springboot.bizconnect.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
