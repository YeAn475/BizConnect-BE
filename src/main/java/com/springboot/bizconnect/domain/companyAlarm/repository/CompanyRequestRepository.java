package com.springboot.bizconnect.domain.companyAlarm.repository;

import com.springboot.bizconnect.entity.CompanyRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRequestRepository extends JpaRepository<CompanyRequest, Long> {
}
