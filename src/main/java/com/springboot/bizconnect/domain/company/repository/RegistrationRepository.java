package com.springboot.bizconnect.domain.company.repository;


import com.springboot.bizconnect.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
}
