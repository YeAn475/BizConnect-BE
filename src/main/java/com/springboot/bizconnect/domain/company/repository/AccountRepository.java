package com.springboot.bizconnect.domain.company.repository;

import com.springboot.bizconnect.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
