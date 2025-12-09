package com.springboot.bizconnect.domain.company.repository;

import com.springboot.bizconnect.entity.BusinessRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessRegistrationRepository extends JpaRepository<BusinessRegistration, Long> {
}
