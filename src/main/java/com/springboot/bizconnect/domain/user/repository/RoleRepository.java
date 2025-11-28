package com.springboot.bizconnect.domain.user.repository;

import com.springboot.bizconnect.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
