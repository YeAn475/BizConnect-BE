package com.springboot.bizconnect.domain.user.repository;

import com.springboot.bizconnect.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Long> {
}
