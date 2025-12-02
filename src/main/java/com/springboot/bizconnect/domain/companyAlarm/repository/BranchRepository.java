package com.springboot.bizconnect.domain.companyAlarm.repository;

import com.springboot.bizconnect.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    Optional<Branch> findByName(String name);
}
