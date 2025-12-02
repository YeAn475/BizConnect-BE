package com.springboot.bizconnect.domain.companyAlarm.repository;

import com.springboot.bizconnect.entity.Affiliation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AffiliationRepository extends JpaRepository<Affiliation, Long> {
    Optional<Affiliation> findByName(String name);
}
