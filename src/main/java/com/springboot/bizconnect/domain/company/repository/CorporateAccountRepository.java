package com.springboot.bizconnect.domain.company.repository;

import com.springboot.bizconnect.entity.CorporateAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CorporateAccountRepository extends JpaRepository<CorporateAccount, Long> {
}
