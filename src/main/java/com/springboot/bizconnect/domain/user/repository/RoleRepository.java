package com.springboot.bizconnect.domain.user.repository;

import com.springboot.bizconnect.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findFirstByRoleNoIn(List<Long> roleNos);
}
