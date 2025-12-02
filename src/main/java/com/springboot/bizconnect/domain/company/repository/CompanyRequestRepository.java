package com.springboot.bizconnect.domain.company.repository;

import com.springboot.bizconnect.entity.CompanyRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRequestRepository extends JpaRepository<CompanyRequest, Long> {
}
